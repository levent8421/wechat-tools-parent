package com.levent8421.wechat.tools.wechat.api.impl;

import com.levent8421.wechat.tools.commons.http.AbstractHttpApi;
import com.levent8421.wechat.tools.wechat.api.WechatApi;
import com.levent8421.wechat.tools.wechat.api.vo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 19:33
 * Class Name: WechatApiImpl
 * Author: Levent8421
 * Description:
 * Wechat Api Implemention
 *
 * @author Levent8421
 */
@Component
public class WechatApiImpl extends AbstractHttpApi implements WechatApi {
    private static final int NONCE_STR_LEN = 7;
    /**
     * Access token
     */
    private static final String API_URL_WECHAT_APP_TOKEN =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * JS API Ticket
     */
    private static final String API_URL_WECHAT_JS_API_TICKET =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    /**
     * 微信OAuth授权地址
     */
    private static final String API_URL_O_AUTH_TOKEN =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 获取微信用户信息的API
     */
    private static final String API_URL_USER_INFO =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";

    @Override
    public WechatAppTokenVo getToken(String appId, String secret) {
        final String apiUrl = String.format(API_URL_WECHAT_APP_TOKEN, appId, secret);
        return get(apiUrl, WechatAppTokenVo.class, resp -> resp.get("errcode") == null);
    }

    @Override
    public WechatJsApiTicketVo getJsApiTicket(String appToken) {
        final String apiUrl = String.format(API_URL_WECHAT_JS_API_TICKET, appToken);
        return get(apiUrl, WechatJsApiTicketVo.class, resp -> resp.get("errcode") == null);
    }

    @Override
    public WechatJsApiConfigParam getJsApiConfigParam(String jsApiTicket, String appId, String url) {
        final String nonceStr = RandomStringUtils.randomAlphanumeric(NONCE_STR_LEN);
        final long timestamp = System.currentTimeMillis() / 1000;
        final String signTemplate = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
        final String signText = String.format(signTemplate, jsApiTicket, nonceStr, timestamp, url);
        final String sign = DigestUtils.sha1Hex(signText);

        final WechatJsApiConfigParam param = new WechatJsApiConfigParam();
        param.setAppId(appId);
        param.setSignature(sign);
        param.setNonceStr(nonceStr);
        param.setTimestamp(timestamp);
        return param;
    }

    @Override
    public WechatOauthToken getOauthToken(String appId, String secret, String oauthCode) {
        final String apiUrl = String.format(API_URL_O_AUTH_TOKEN, appId, secret, oauthCode);
        return get(apiUrl, WechatOauthToken.class, res -> res.get("openid") != null && res.get("access_token") != null);
    }

    @Override
    public WechatUserInfo getUserInfo(String accessToken, String openId, String lang) {
        String apiUrl = String.format(API_URL_USER_INFO, accessToken, openId, lang);
        return get(apiUrl, WechatUserInfo.class, res -> res.get("errcode") == null);
    }
}
