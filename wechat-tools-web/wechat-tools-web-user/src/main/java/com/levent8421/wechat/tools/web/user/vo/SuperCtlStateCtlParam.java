package com.levent8421.wechat.tools.web.user.vo;

import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlDeviceStatus;
import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/11/25 18:04
 * ClassName: SuperCtlStateCtlParam
 * Description:
 * SuperCtl 状态控制参数
 *
 * @author levent8421
 */
@Data
public class SuperCtlStateCtlParam {
    private Integer deviceId;
    private SuperCtlDeviceStatus status;
}
