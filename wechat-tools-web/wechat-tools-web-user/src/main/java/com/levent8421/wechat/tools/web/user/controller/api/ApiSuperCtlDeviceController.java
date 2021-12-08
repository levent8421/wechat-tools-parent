package com.levent8421.wechat.tools.web.user.controller.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlWeatherService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlDeviceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final SuperCtlWeatherService superCtlWeatherService;

    public ApiSuperCtlDeviceController(SuperCtlDeviceService superCtlDeviceService,
                                       TokenDataHolder tokenDataHolder,
                                       SuperCtlWeatherService superCtlWeatherService) {
        this.superCtlDeviceService = superCtlDeviceService;
        this.tokenDataHolder = tokenDataHolder;
        this.superCtlWeatherService = superCtlWeatherService;
    }

    /**
     * Find user for current user
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<SuperCtlDeviceInfo>> devicesForCurrentUser() {
        Integer uid = requireUserId(tokenDataHolder);
        List<SuperCtlDevice> devices = superCtlDeviceService.findByUser(uid);
        Set<String> addressArr = Sets.newHashSet();
        for (SuperCtlDevice device : devices) {
            addressArr.add(device.getAddressCode());
        }
        Map<String, SuperCtlWeather> weatherTable = superCtlWeatherService.getWeathers(addressArr);
        List<SuperCtlDeviceInfo> res = Lists.newArrayList();
        for (SuperCtlDevice device : devices) {
            SuperCtlDeviceInfo info = new SuperCtlDeviceInfo();
            info.setDevice(device);
            info.setWeather(weatherTable.get(device.getAddressCode()));
            res.add(info);
        }
        return GeneralResult.ok(res);
    }
}
