package com.levent8421.wechat.tools.web.user.controller.api;

import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.utils.datetime.DateTimeUtils;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.model.service.app.sc.define.MotorStates;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionStatus;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionTypes;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlCreateTaskActionParam;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlStateCtlParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/25 18:03
 * ClassName: ApiSuperCtlActionController
 * Description:
 * Action Web Controller
 *
 * @author levent8421
 */
@RestController
@RequestMapping("/api/token/sc-action")
@Slf4j
public class ApiSuperCtlActionController extends AbstractApiController {
    private static final int MIN_TASK_OFFSET = 60 * 1000;

    private final SuperCtlActionService superCtlActionService;
    private final SuperCtlDeviceService superCtlDeviceService;
    private final DeviceMessageClient deviceMessageClient;

    public ApiSuperCtlActionController(SuperCtlActionService superCtlActionService,
                                       SuperCtlDeviceService superCtlDeviceService,
                                       DeviceMessageClient deviceMessageClient) {
        this.superCtlActionService = superCtlActionService;
        this.superCtlDeviceService = superCtlDeviceService;
        this.deviceMessageClient = deviceMessageClient;
    }

    /**
     * Send action
     *
     * @param param params
     * @return GR
     */
    @PostMapping("/_state-ctl")
    public GeneralResult<SuperCtlAction> stateCtl(@RequestBody SuperCtlStateCtlParam param) {
        Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "null params!");
        ParamChecker.notNull(param.getDeviceId(), e, "deviceId params!");
        ParamChecker.notNull(param.getStatus(), e, "status params!");
        SuperCtlDevice device = superCtlDeviceService.require(param.getDeviceId());
        SuperCtlDeviceStatus status = param.getStatus();
        SuperCtlAction action = superCtlActionService.sendAction(device, status, deviceMessageClient);
        return GeneralResult.ok(action);
    }

    /**
     * Find action by type with pagination
     *
     * @param type type
     * @param page page
     * @param rows rows
     * @return GR
     */
    @GetMapping("/_by-type")
    public GeneralResult<PageInfo<SuperCtlAction>> findByType(@RequestParam("type") String type,
                                                              @RequestParam("state") String state,
                                                              @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(required = false, value = "rows", defaultValue = "1") Integer rows) {

        PageInfo<SuperCtlAction> res = superCtlActionService.findByTypeAndState(type, state, page, rows);
        return GeneralResult.ok(res);
    }

    /**
     * 预约设备任务
     *
     * @param param params @see com.levent8421.wechat.tools.web.user.vo.SuperCtlCreateTaskActionParam
     * @return GR
     */
    @PutMapping("/_order-task")
    public GeneralResult<SuperCtlAction> orderTask(@RequestBody SuperCtlCreateTaskActionParam param) {
        checkOrderTaskParams(param);
        Integer deviceId = param.getDeviceId();
        SuperCtlDevice device = superCtlDeviceService.require(deviceId);

        SuperCtlAction action = new SuperCtlAction();
        action.setDeviceId(param.getDeviceId());
        action.setTypeCode(SuperCtlActionTypes.TASK_CTL);
        action.setStateJsonBefore(device.getStatusJson());
        action.setStateJsonExpect(param.getTargetStatus().asString());
        action.setOrderTime(param.getOrderTime());
        action.setStartTime(DateTimeUtils.now());
        action.setStateCode(SuperCtlActionStatus.STATE_START);
        superCtlActionService.sendAction(device, action, deviceMessageClient);
        return GeneralResult.ok(action);
    }

    private void checkOrderTaskParams(SuperCtlCreateTaskActionParam param) {
        Class<BadRequestException> e = BadRequestException.class;
        Date orderTime = param.getOrderTime();
        ParamChecker.notNull(param, e, "Can not resolve empty params!");
        ParamChecker.notNull(param.getDeviceId(), e, "deviceId is required!");
        ParamChecker.notNull(orderTime, e, "orderTime is required!");
        ParamChecker.notNull(param.getTargetStatus(), e, "targetStatus is required!");
        Date now = DateTimeUtils.now();
        long orderTimestamp = orderTime.getTime();
        if ((orderTimestamp - now.getTime()) < MIN_TASK_OFFSET) {
            log.debug("Giving date=[{}],now=[{}]", DateTimeUtils.format(orderTime, DateTimeUtils.DEFAULT_FORMAT),
                    DateTimeUtils.format(now, DateTimeUtils.DEFAULT_FORMAT));
            throw new BadRequestException("orderTime should be in a minute!");
        }
        SuperCtlDeviceStatus status = param.getTargetStatus();
        ParamChecker.notEmpty(status.getMotor1(), e, "targetStatus.motor1 is required!");
        ParamChecker.notEmpty(status.getMotor2(), e, "targetStatus.motor2 is required!");
        if (!MotorStates.isStateInRule(status.getMotor1())) {
            throw new BadRequestException("Invalidate motor1 state:" + status.getMotor1());
        }
        if (!MotorStates.isStateInRule(status.getMotor2())) {
            throw new BadRequestException("Invalidate motor2 state:" + status.getMotor1());
        }
    }
}
