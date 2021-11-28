package com.levent8421.wechat.tools.web.user.vo;

import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/11/28 15:32
 * ClassName: MotorCtlParam
 * Description:
 * 设备控制参数
 *
 * @author levent8421
 */
@Data
public class MotorCtlParam {
    private Integer deviceId;
    private Integer motor;
    private String state;
}
