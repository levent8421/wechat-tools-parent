package com.levent8421.wechat.tools.wechat.api.impl;

import com.levent8421.wechat.tools.commons.http.AbstractHttpApi;
import com.levent8421.wechat.tools.wechat.api.WechatApi;
import com.levent8421.wechat.tools.wechat.api.vo.WechatAppTokenVo;
import com.levent8421.wechat.tools.wechat.api.vo.WechatJsApiTicketVo;
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
}
