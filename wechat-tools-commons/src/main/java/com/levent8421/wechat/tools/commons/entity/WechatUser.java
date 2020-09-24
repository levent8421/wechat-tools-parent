package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:11
 * Class Name: WechatUser
 * Author: Levent8421
 * Description:
 * 用户实体类
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_wechat_user")
public class WechatUser extends AbstractEntity {
    /**
     * 商户ID
     */
    @Column(name = "merchant_id", length = 10, nullable = false)
    private Integer merchantId;
    /**
     * 商户信息
     */
    private Merchant merchant;
    /**
     * 微信昵称
     */
    @Column(name = "w_nickname")
    private String wNickname;
    /**
     * 微信头像
     */
    @Column(name = "w_avatar")
    private String wAvatar;
    /**
     * 微信OpenId
     */
    @Column(name = "w_open_id", nullable = false)
    private String wOpenId;
    /**
     * yarn
     * 微信Union ID
     */
    @Column(name = "w_union_id")
    private String wUnionId;
    /**
     * 微信用户元数据
     */
    @Column(name = "w_metadata")
    private String wMetadata;
}
