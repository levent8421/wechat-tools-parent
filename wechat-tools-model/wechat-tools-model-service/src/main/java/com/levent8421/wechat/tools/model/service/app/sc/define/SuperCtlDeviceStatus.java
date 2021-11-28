package com.levent8421.wechat.tools.model.service.app.sc.define;

import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/11/25 17:35
 * ClassName: SuperCtlDeviceStatus
 * Description:
 * Super Ctl Device Status
 *
 * @author levent8421
 */
@Data
public class SuperCtlDeviceStatus {
    public static final int MOTOR1 = 1;
    public static final int MOTOR2 = 2;
    private String motor1;
    private String motor2;
}
