package com.levent8421.wechat.tools.web.user.vo;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/12/7 17:39
 * ClassName: SuperCtlDeviceInfo
 * Description:
 * SuperCtl device info
 *
 * @author levent8421
 */
@Data
public class SuperCtlDeviceInfo {
    private SuperCtlDevice device;
    private SuperCtlWeather weather;
}
