package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Create By Levent8421
 * Create Time: 2020/9/13 16:02
 * Class Name: InviteFollowLog
 * Author: Levent8421
 * Description:
 * 邀请关注记录
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "t_invite_follow_log")
public class InviteFollowLog extends AbstractEntity {
    /**
     * 关注用户ID
     */
    @Column(name = "user_id", length = 10, nullable = false)
    private Integer userId;
    /**
     * 邀请人ID
     */
    @Column(name = "invite_id", length = 10, nullable = false)
    private Integer inviterId;
    /**
     * 抽奖应用ID
     */
    @Column(name = "app_id", length = 10, nullable = false)
    private Integer appId;
    /**
     * 商户ID
     */
    @Column(name = "merchant_id", length = 10, nullable = false)
    private Integer merchantId;
    /**
     * 是否仍在保持关注
     */
    @Column(name = "stay_focused", nullable = false)
    private Boolean stayFocused;
}
