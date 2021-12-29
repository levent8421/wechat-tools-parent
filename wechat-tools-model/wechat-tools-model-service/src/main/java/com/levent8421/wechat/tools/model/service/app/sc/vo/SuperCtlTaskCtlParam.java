package com.levent8421.wechat.tools.model.service.app.sc.vo;

import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import lombok.Data;

import java.util.Date;

/**
 * Create By leven ont 2021/12/29 23:09
 * Class Name :[SuperCtlTaskCtlParam]
 * <p>
 * 任务控制参数
 *
 * @author leven
 */
@Data
public class SuperCtlTaskCtlParam {
    private Date orderTime;
    private SuperCtlDeviceStatus status;
}
