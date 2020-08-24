package com.levent8421.wechat.tools.wechat.token.cache;

import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;

import java.util.function.Function;

/**
 * Create By Levent8421
 * Create Time: 2020/8/24 11:53
 * Class Name: WechatTokenCache
 * Author: Levent8421
 * Description:
 * 微信令牌缓存
 *
 * @author Levent8421
 */
public interface WechatTokenCache {
    /**
     * 缓存一个APP Token
     *
     * @param merchantId merchant id
     * @param token      token
     */
    void put(Integer merchantId, WechatAppToken token);

    /**
     * 缓存一个JS API Ticket
     *
     * @param merchantId 商户ID
     * @param ticket     ticket
     */
    void put(Integer merchantId, WechatJsApiTicket ticket);

    /**
     * 从缓存中获取一个APP Token
     *
     * @param merchantId           商户ID
     * @param ifExpiredOrNotExists 当该缓存不存在时调用该方法，将调用结果放入缓存并返回
     * @return APP Token
     */
    WechatAppToken getAppToken(Integer merchantId, Function<Integer, WechatAppToken> ifExpiredOrNotExists);

    /**
     * 从缓存中获取一个Js API Ticket
     *
     * @param merchantId           商户ID
     * @param ifExpiredOrNotExists 当该缓存不存在时调用该方法，将调用结果放入缓存并返回
     * @return APP Token
     */
    WechatJsApiTicket getJsApiTicket(Integer merchantId, Function<Integer, WechatJsApiTicket> ifExpiredOrNotExists);
}
