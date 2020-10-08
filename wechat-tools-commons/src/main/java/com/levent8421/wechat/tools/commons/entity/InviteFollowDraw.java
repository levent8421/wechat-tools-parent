package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Create By Levent8421
 * Create Time: 2020/10/7 17:07
 * Class Name: InviteFollowDraw
 * Author: Levent8421
 * Description:
 * 抽奖动作实体类
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "t_invite_follow_draw")
public class InviteFollowDraw extends AbstractEntity {
    /**
     * 状态： 已中奖
     */
    public static final int STATE_WON = 0x01;
    /**
     * 状态：未中奖
     */
    public static final int STATE_NOT_WON = 0x02;
    /**
     * 状态： 已兑奖
     */
    public static final int STATE_PRIZE_PAID = 0x03;

    /**
     * App　Id
     */
    @Column(name = "invite_follow_app_id", length = 10, nullable = false)
    private Integer inviteFollowAppId;
    private InviteFollowApp inviteFollowApp;
    /**
     * 微型用户ID
     */
    @Column(name = "wechat_user", length = 10, nullable = false)
    private Integer wechatUserId;
    private WechatUser wechatUser;
    /**
     * 状态
     */
    @Column(name = "state", length = 2, nullable = false)
    private Integer state;
    /**
     * 奖品ID
     */
    @Column(name = "prize_id", length = 10)
    private Integer prizeId;
    /**
     * 电话号码
     */
    @Column(name = "phone", length = 50)
    private String phone;
}
