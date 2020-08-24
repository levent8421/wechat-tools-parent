package com.levent8421.wechat.tools.wechat.api;

import com.levent8421.wechat.tools.wechat.api.vo.WechatAppTokenVo;
import com.levent8421.wechat.tools.wechat.api.vo.WechatJsApiTicketVo;

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
}
