package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlStateCtlParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ApiSuperCtlActionController(SuperCtlActionService superCtlActionService,
                                       SuperCtlDeviceService superCtlDeviceService) {
        this.superCtlActionService = superCtlActionService;
        this.superCtlDeviceService = superCtlDeviceService;
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
        SuperCtlAction action = superCtlActionService.sendAction(device, status);
        return GeneralResult.ok(action);
    }
}
