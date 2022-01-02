package com.levent8421.wechat.tools.web.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import lombok.Data;

import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2022/1/2 13:33
 * ClassName: SuperCtlDeviceStatusSyncResult
 * Description:
 * 设备状态同步返回结果
 *
 * @author levent8421
 */
@Data
public class SuperCtlDeviceStatusSyncResult {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private SuperCtlDeviceStatus status;
}
