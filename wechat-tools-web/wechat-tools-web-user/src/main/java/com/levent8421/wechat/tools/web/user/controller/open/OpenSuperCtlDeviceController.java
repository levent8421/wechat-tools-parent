package com.levent8421.wechat.tools.web.user.controller.open;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Levent8421
 * Date: 2021/12/29 10:47
 * ClassName: OpenSuperCtlDeviceController
 * Description:
 * Super Ctl Device Open Access Web Controller
 *
 * @author levent8421
 */
@RestController
@RequestMapping("/api/open/sc-device")
public class OpenSuperCtlDeviceController extends AbstractApiController {
    private final SuperCtlDeviceService superCtlDeviceService;

    public OpenSuperCtlDeviceController(SuperCtlDeviceService superCtlDeviceService) {
        this.superCtlDeviceService = superCtlDeviceService;
    }

    /**
     * Get Device Status by sn
     *
     * @param sn sn
     * @return GR
     */
    @GetMapping("/_status-via-sn")
    public GeneralResult<SuperCtlDeviceStatus> getDeviceStatusBySn(@RequestParam("sn") String sn) {
        SuperCtlDevice device = superCtlDeviceService.findBySn(sn);
        if (device == null) {
            return GeneralResult.notFound("Device Not found:" + sn);
        }
        SuperCtlDeviceStatus status = SuperCtlDeviceStatus.fromString(device.getStatusJson());
        return GeneralResult.ok(status);
    }
}
