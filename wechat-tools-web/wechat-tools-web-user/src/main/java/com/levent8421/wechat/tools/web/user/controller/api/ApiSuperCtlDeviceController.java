package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/23 10:15
 * ClassName: ApiSuperCtlDeviceController
 * Description:
 * SuperCtl APP Device WebController
 *
 * @author levent8421
 */
@RestController
@RequestMapping("/api/token/sc-device")
public class ApiSuperCtlDeviceController extends AbstractUserController {
    private final SuperCtlDeviceService superCtlDeviceService;
    private final TokenDataHolder tokenDataHolder;

    public ApiSuperCtlDeviceController(SuperCtlDeviceService superCtlDeviceService,
                                       TokenDataHolder tokenDataHolder) {
        this.superCtlDeviceService = superCtlDeviceService;
        this.tokenDataHolder = tokenDataHolder;
    }

    /**
     * Find user for current user
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<SuperCtlDevice>> devicesForCurrentUser() {
        Integer uid = requireUserId(tokenDataHolder);
        List<SuperCtlDevice> devices = superCtlDeviceService.findByUser(uid);
        return GeneralResult.ok(devices);
    }
}
