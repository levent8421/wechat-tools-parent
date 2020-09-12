package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 14:59
 * Class Name: WechatUserService
 * Author: Levent8421
 * Description:
 * 微信用户相关业务行为定义
 *
 * @author Levent8421
 */
public interface WechatUserService extends AbstractService<WechatUser> {
    /**
     * 刷新用户信息（从微信服务器）
     *
     * @param merchant  商户
     * @param oAuthCode OAuth code
     * @return user
     */
    WechatUser refreshUserInfo(Merchant merchant, String oAuthCode);

    /**
     * Find User By OpenId
     *
     * @param merchant 商户
     * @param openId   openId
     * @return user
     */
    WechatUser findByOpenId(Merchant merchant, String openId);

    /**
     * 查询用户信息
     * 用户不存在时创建用户
     *
     * @param merchant 商户
     * @param openId   openId
     * @return user
     */
    WechatUser findOrCreateByOpenId(Merchant merchant, String openId);

    /**
     * 刷新用户信息
     *
     * @param merchant   商户
     * @param user       用户
     * @param oAuthToken OAuthToken
     * @return user
     */
    WechatUser refreshUserInfo(Merchant merchant, WechatUser user, String oAuthToken);
}
