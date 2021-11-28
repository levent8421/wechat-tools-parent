package com.levent8421.wechat.tools.web.user.controller.open;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.vo.ActionDoneReportParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Create by Levent8421
 * Date: 2021/11/28 17:50
 * ClassName: OpenSuperCtlActionController
 * Description:
 * Action Controller
 *
 * @author levent8421
 */
@Slf4j
@RestController
@RequestMapping("/api/open/sc-action")
public class OpenSuperCtlActionController extends AbstractApiController {
    private final SuperCtlActionService superCtlActionService;

    public OpenSuperCtlActionController(SuperCtlActionService superCtlActionService) {
        this.superCtlActionService = superCtlActionService;
    }

    /**
     * 通知操作完成
     *
     * @param id    action id
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/_done")
    public GeneralResult<Void> reportActionDone(@PathVariable("id") Integer id, @RequestBody ActionDoneReportParam param) {
        Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "request body is required!");
        SuperCtlDeviceStatus status = param.getStatus();
        ParamChecker.notNull(status, e, "status is required!");
        ParamChecker.notNull(status.getMotor1(), e, "status.motor1 is required!");
        ParamChecker.notNull(status.getMotor2(), e, "status.motor2 is required!");

        superCtlActionService.reportActionDone(id, param.getMsg(), status);
        log.info("Action done:[{}]", id);
        return GeneralResult.ok();
    }
}
