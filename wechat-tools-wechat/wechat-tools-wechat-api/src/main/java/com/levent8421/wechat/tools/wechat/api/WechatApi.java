package com.levent8421.wechat.tools.wechat.api;

import com.levent8421.wechat.tools.wechat.api.vo.*;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 19:31
 * Class Name: WechatApi
 * Author: Levent8421
 * Description:
 * 微信API
 *
 * @author Levent8421
 */
public interface WechatApi {
    /**
     * 获取微信AccessToken
     *
     * @param appId  AppId
     * @param secret Secret
     * @return WxAccessToken
     */
    WechatAppTokenVo getToken(String appId, String secret);

    /**
     * Fetch jsApi ticket
     *
     * @param appToken app token
     * @return ticket
     */
    WechatJsApiTicketVo getJsApiTicket(String appToken);

    /**
     * 获取JS API配置参数
     *
     * @param jsApiTicket js api ticket
     * @param url         url
     * @param appId       App Id
     * @return configParams
     */
    WechatJsApiConfigParam getJsApiConfigParam(String jsApiTicket, String appId, String url);

    /**
     * 获取OAuth用户token
     *
     * @param appId     appid
     * @param secret    secret
     * @param oauthCode oAuth code
     * @return oAuthToken
     */
    WechatOauthToken getOauthToken(String appId, String secret, String oauthCode);

    /**
     * 获取用户信息
     *
     * @param accessToken API令牌
     * @param openId      用户openId
     * @param lang        语言
     * @return 用户信息
     */
    WechatUserInfo getUserInfo(String accessToken, String openId, String lang);
}
