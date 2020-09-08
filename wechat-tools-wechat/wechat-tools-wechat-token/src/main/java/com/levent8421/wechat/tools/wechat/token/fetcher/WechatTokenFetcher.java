package com.levent8421.wechat.tools.wechat.token.fetcher;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;

/**
 * Create By Levent8421
 * Create Time: 2020/8/23 23:03
 * Class Name: WechatTokenFetcher
 * Author: Levent8421
 * Description:
 * 微信令牌抓取器
 *
 * @author Levent8421
 */
public interface WechatTokenFetcher {
    /**
     * 策略代码
     *
     * @return strategy Code
     */
    int getStrategyCode();

    /**
     * 获取微信APP Token
     *
     * @param strategy 获取策略
     * @param merchant 商户信息
     * @return token
     */
    WechatAppToken fetchAppToken(Merchant merchant, WechatTokenFetchStrategy strategy);

    /**
     * 获取微信 JS API Ticket
     *
     * @param strategy 获取策略
     * @param merchant 商户信息
     * @return ticket
     */
    WechatJsApiTicket fetchJsApiTicket(Merchant merchant, WechatTokenFetchStrategy strategy);
}
