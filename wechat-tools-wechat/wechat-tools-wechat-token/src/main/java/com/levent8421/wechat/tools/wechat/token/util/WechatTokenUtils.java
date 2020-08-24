package com.levent8421.wechat.tools.wechat.token.util;

import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 1:32
 * Class Name: WechatTokenUtils
 * Author: Levent8421
 * Description:
 * 微信令牌工具类
 *
 * @author Levent8421
 */
public class WechatTokenUtils {
    /**
     * 判断APP Token 是否过期
     *
     * @param token token
     * @return expired
     */
    public static boolean isExpired(WechatAppToken token) {
        final Long expireIn = token.getExpireIn();
        if (expireIn == null) {
            return true;
        }
        final long now = System.currentTimeMillis();
        return (token.getFetchTime() + expireIn) < now;
    }

    /**
     * 判断JS API Ticket是否过期
     *
     * @param ticket ticket
     * @return expired
     */
    public static boolean isExpired(WechatJsApiTicket ticket) {
        final Long expireIn = ticket.getExpireIn();
        if (expireIn == null) {
            return true;
        }
        final long now = System.currentTimeMillis();
        return (ticket.getFetchTime() + expireIn) < now;
    }
}
