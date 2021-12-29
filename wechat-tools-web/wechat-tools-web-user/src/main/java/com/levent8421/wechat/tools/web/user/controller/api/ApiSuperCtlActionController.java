package com.levent8421.wechat.tools.web.user.controller.api;

import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlStateCtlParam;
import org.springframework.web.bind.annotation.*;

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
public class ApiSuperCtlActionController extends AbstractApiController {
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
                                                              @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(required = false, value = "rows", defaultValue = "1") Integer rows) {

        PageInfo<SuperCtlAction> res = superCtlActionService.findByType(type, page, rows);
        return GeneralResult.ok(res);
    }
}
