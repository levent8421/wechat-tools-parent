package com.levent8421.wechat.tools.wechat.token.fetcher;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By leven ont 2020/9/7 0:52
 * Class Name :[WechatTokenFetchers]
 * 微信token获取器工具
 *
 * @author leven
 */
@Component
public class WechatTokenFetchers {
    private final List<WechatTokenFetcher> fetchers;
    private final Map<Integer, WechatTokenFetcher> fetcherTable = new HashMap<>(16);

    public WechatTokenFetchers(List<WechatTokenFetcher> fetchers) {
        this.fetchers = fetchers;
        loadFetchers();
    }

    private void loadFetchers() {
        for (WechatTokenFetcher fetcher : fetchers) {
            if (fetcher == null) {
                continue;
            }
            if (fetcherTable.containsKey(fetcher.getStrategyCode())) {
                final WechatTokenFetcher exists = fetcherTable.get(fetcher.getStrategyCode());
                final String error = String.format("Duplicate TokenFetcher for strategyCode[%d],[%s] and [%s]",
                        fetcher.getStrategyCode(), fetcher.getClass().getName(), exists.getClass().getName());
                throw new IllegalStateException(error);
            }
            fetcherTable.put(fetcher.getStrategyCode(), fetcher);
        }
    }

    private WechatTokenFetcher findFetcher(Integer strategyCode) {
        if (fetcherTable.containsKey(strategyCode)) {
            throw new IllegalArgumentException("Can not find tokenFetcher for strategyCode " + strategyCode);
        }
        return fetcherTable.get(strategyCode);
    }

    /**
     * 获取微信公众号APP token
     *
     * @param strategy 策略
     * @return AppToken
     */
    public WechatAppToken fetchAppToken(WechatTokenFetchStrategy strategy) {
        final Integer strategyCode = strategy.getStrategyCode();
        return findFetcher(strategyCode).fetchAppToken(strategy);
    }

    /**
     * 获取JsApiTicket
     *
     * @param strategy 策略
     * @return js Api Ticket
     */
    public WechatJsApiTicket fetchJsApiTicket(WechatTokenFetchStrategy strategy) {
        final Integer strategyCode = strategy.getStrategyCode();
        return findFetcher(strategyCode).fetchJsApiTicket(strategy);
    }
}
