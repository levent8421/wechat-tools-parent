package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:30
 * Class Name: InviteFollowPrize
 * Author: Levent8421
 * Description:
 * 关注抽奖应用APP奖品
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_invite_follow_prize")
public class InviteFollowPrize extends AbstractEntity {
    /**
     * 状态： 可用
     */
    public static final int STATE_AVAILABLE = 0x01;
    /**
     * 状态： 不可用
     */
    public static final int STATE_NOT_AVAILABLE = 0x02;
    /**
     * APP ID
     */
    @Column(name = "invite_follow_app_id", length = 10, nullable = false)
    private Integer inviteFollowAppId;
    /**
     * Prize name
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 奖品ICON
     */
    @Column(name = "image")
    private String image;
    /**
     * 奖品总量
     */
    @Column(name = "total_stock", length = 5, nullable = false)
    private Integer totalStock;
    /**
     * 中奖率
     */
    @Column(name = "winning_rate", nullable = false, length = 3)
    private Integer winningRate;
    /**
     * 已中奖数量
     */
    @Column(name = "sales", nullable = false, length = 5)
    private Integer sales;
    /**
     * 状态码
     */
    @Column(name = "state", length = 2, nullable = false)
    private Integer state;
}
