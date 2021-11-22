package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:30
 * ClassName: SuperCtlDevice
 * Description:
 * SuperCtl APP Device
 *
 * @author levent8421
 */
@Table(name = "t_super_ctl_device")
@EqualsAndHashCode(callSuper = true)
@Data
public class SuperCtlDevice extends AbstractEntity {
    /**
     * 微信用户ID
     */
    @Column(name = "wechat_user_id", length = 10, nullable = false)
    private Integer wechatUserId;
    /**
     * Device Name
     */
    @Column(name = "device_name", nullable = false)
    private String deviceName;
    /**
     * 设备地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 最后一次心跳时间
     */
    @Column(name = "last_heartbeat_time")
    private Date lastHeartbeatTime;
    /**
     * 状态JSON
     */
    @Column(name = "status_json", nullable = false)
    private String statusJson;
}
