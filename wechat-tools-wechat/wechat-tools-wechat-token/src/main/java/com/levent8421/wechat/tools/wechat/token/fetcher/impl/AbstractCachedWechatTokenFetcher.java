package com.levent8421.wechat.tools.wechat.token.fetcher.impl;

import com.levent8421.wechat.tools.cache.HashCache;
import com.levent8421.wechat.tools.cache.memory.MemoryHashCache;
import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.wechat.token.fetcher.WechatTokenFetcher;
import com.levent8421.wechat.tools.wechat.token.util.WechatTokenUtils;
import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 1:26
 * Class Name: AbstractCachedWechatTokenFetcher
 * Author: Levent8421
 * Description:
 * 带缓存的微信令牌抓取器
 *
 * @author Levent8421
 */
public abstract class AbstractCachedWechatTokenFetcher implements WechatTokenFetcher {
    private final HashCache<WechatAppToken> tokenCache;
    private final HashCache<WechatJsApiTicket> ticketCache;

    protected AbstractCachedWechatTokenFetcher() {
        tokenCache = new MemoryHashCache<>();
        ticketCache = new MemoryHashCache<>();
    }

    @Override
    public WechatAppToken fetchAppToken(WechatTokenFetchStrategy strategy) {
        final String key = String.valueOf(strategy.getId());
        final WechatAppToken cachedToken = tokenCache.get(key);
        final WechatAppToken token;
        if (cachedToken == null || WechatTokenUtils.isExpired(cachedToken)) {
            token = doFetchAppToken(strategy);
        } else {
            token = cachedToken;
        }
        return token;
    }

    /**
     * 执行抓取操作
     *
     * @param strategy 抓取策略
     * @return app token
     */
    protected abstract WechatAppToken doFetchAppToken(WechatTokenFetchStrategy strategy);

    @Override
    public WechatJsApiTicket fetchJsApiTicket(WechatTokenFetchStrategy strategy) {
        final String key = String.valueOf(strategy.getId());
        final WechatJsApiTicket cachedTicked = ticketCache.get(key);
        final WechatJsApiTicket ticket;
        if (cachedTicked == null || WechatTokenUtils.isExpired(cachedTicked)) {
            ticket = doFetchJsApiTicket(strategy);
        } else {
            ticket = cachedTicked;
        }
        return ticket;
    }

    /**
     * 执行抓取操作
     *
     * @param strategy 抓取策略
     * @return ticket
     */
    protected abstract WechatJsApiTicket doFetchJsApiTicket(WechatTokenFetchStrategy strategy);
}
