package com.levent8421.wechat.tools.model.service.app.sc.define;

import com.alibaba.fastjson.JSON;
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
    public static final String DEFAULT_STATUS;

    static {
        SuperCtlDeviceStatus status = new SuperCtlDeviceStatus();
        status.setMotor1(MotorStates.STATE_OFF);
        status.setMotor2(MotorStates.STATE_OFF);
        DEFAULT_STATUS = status.asString();
    }

    public static SuperCtlDeviceStatus fromString(String str) {
        return JSON.parseObject(str, SuperCtlDeviceStatus.class);
    }

    private String motor1;
    private String motor2;

    public String asString() {
        return JSON.toJSONString(this);
    }
}
