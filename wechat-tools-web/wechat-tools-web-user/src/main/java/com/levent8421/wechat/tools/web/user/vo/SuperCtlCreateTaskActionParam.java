package com.levent8421.wechat.tools.web.user.vo;

import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import lombok.Data;

import java.util.Date;

/**
 * Create By leven ont 2022/1/2 20:47
 * Class Name :[SuperCtlCreateTaskActionParam]
 * <p>
 * 预约任务参数
 *
 * @author leven
 */
@Data
public class SuperCtlCreateTaskActionParam {
    private Integer deviceId;
    private Date orderTime;
    private SuperCtlDeviceStatus targetStatus;
}
