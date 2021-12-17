package com.levent8421.wechat.tools.web.user.controller.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlWeatherService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import com.levent8421.wechat.tools.web.user.vo.SuperCtlDeviceInfo;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get Device Info
     *
     * @param id id
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<SuperCtlDevice> getDevice(@PathVariable("id") Integer id) {
        SuperCtlDevice device = superCtlDeviceService.require(id);
        return GeneralResult.ok(device);
    }

    /**
     * Update deviceIndo
     *
     * @param id    id
     * @param param params
     * @return GR
     */
    @PostMapping("/{id}")
    public GeneralResult<SuperCtlDevice> updateDevice(@PathVariable("id") Integer id,
                                                      @RequestBody SuperCtlDevice param) {
        Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "empty param!");
        ParamChecker.notEmpty(param.getAddressCode(), e, "addressCode is required!");
        ParamChecker.notEmpty(param.getAddress(), e, "address is required!");
        ParamChecker.notEmpty(param.getDeviceName(), e, "deviceName is required!");
        SuperCtlDevice device = superCtlDeviceService.require(id);
        device.setDeviceName(param.getDeviceName());
        device.setAddress(param.getAddress());
        device.setAddressCode(param.getAddressCode());
        device = superCtlDeviceService.updateById(device);
        return GeneralResult.ok(device);
    }

    /**
     * 用户认领设备
     *
     * @param param params
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<SuperCtlDevice> createDevice(@RequestBody SuperCtlDevice param) {
        Integer uid = requireUserId(tokenDataHolder);
        Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "empty params!");
        ParamChecker.notEmpty(param.getSn(), e, "sn is required!");
        ParamChecker.notEmpty(param.getDeviceName(), e, "deviceName is required!");
        ParamChecker.notEmpty(param.getAddress(), e, "address is required!");
        ParamChecker.notEmpty(param.getAddressCode(), e, "addressCode is required!");
        SuperCtlDevice device = new SuperCtlDevice();
        device.setWechatUserId(uid);
        device.setDeviceName(param.getDeviceName());
        device.setSn(param.getSn());
        device.setAddress(param.getAddress());
        device.setAddressCode(param.getAddressCode());
        device.setStatusJson(SuperCtlDeviceStatus.DEFAULT_STATUS);
        SuperCtlDevice res = superCtlDeviceService.bindNewDevice(device);
        return GeneralResult.ok(res);
    }
}
