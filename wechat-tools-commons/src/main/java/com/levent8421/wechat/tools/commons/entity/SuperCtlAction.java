package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:47
 * ClassName: SuperCtlAction
 * Description:
 * SuperCtl APP Action
 *
 * @author levent8421
 */
@Table(name = "t_super_ctl_action")
@EqualsAndHashCode(callSuper = true)
@Data
public class SuperCtlAction extends AbstractEntity {
    /**
     * Device ID
     */
    @Column(name = "device_id", length = 10, nullable = false)
    private Integer deviceId;
    private SuperCtlDevice device;
    /**
     * action type code
     */
    @Column(name = "type_code", length = 100, nullable = false)
    private String typeCode;
    /**
     * state json(before)
     */
    @Column(name = "state_json_before")
    private String stateJsonBefore;
    /**
     * state json(before)
     */
    @Column(name = "state_json_expect", nullable = false)
    private String stateJsonExpect;
    /**
     * state json(before)
     */
    @Column(name = "state_json_after")
    private String stateJsonAfter;
    /**
     * 预约时间
     */
    @Column(name = "order_time")
    private Date orderTime;
    /**
     * 发起时间
     */
    @Column(name = "start_time", nullable = false)
    private Date startTime;
    /**
     * 完成时间
     */
    @Column(name = "complete_time")
    private Date completeTime;
    /**
     * 状态码
     */
    @Column(name = "state_code", length = 100, nullable = false)
    private String stateCode;
    /**
     * 响应消息
     */
    @Column(name = "response_msg")
    private String responseMsg;
}
