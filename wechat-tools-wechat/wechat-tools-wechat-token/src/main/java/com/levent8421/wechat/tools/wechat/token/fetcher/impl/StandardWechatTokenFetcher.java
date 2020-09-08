package com.levent8421.wechat.tools.wechat.token.fetcher.impl;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.wechat.api.WechatApi;
import com.levent8421.wechat.tools.wechat.api.vo.WechatAppTokenVo;
import com.levent8421.wechat.tools.wechat.api.vo.WechatJsApiTicketVo;
import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/8/23 23:37
 * Class Name: StandardWechatTokenFetcher
 * Author: Levent8421
 * Description:
 * 标准微信令牌获取器
 *
 * @author Levent8421
 */
@Component
public class StandardWechatTokenFetcher extends AbstractCachedWechatTokenFetcher {
    private final WechatApi wechatApi;
    /**
     * 策略码
     */
    private static final int STRATEGY_CODE = 0x01;

    public StandardWechatTokenFetcher(WechatApi wechatApi) {
        this.wechatApi = wechatApi;
    }

    @Override
    public int getStrategyCode() {
        return STRATEGY_CODE;
    }

    @Override
    protected WechatAppToken doFetchAppToken(Merchant merchant, WechatTokenFetchStrategy strategy) {
        final String appId = merchant.getWechatAppId();
        final String secret = merchant.getWechatSecret();
        final WechatAppTokenVo tokenVo = wechatApi.getToken(appId, secret);

        final WechatAppToken token = convert2Token(tokenVo);
        token.setStrategyId(strategy.getId());
        token.setFetchTime(System.currentTimeMillis());
        return token;
    }

    private WechatAppToken convert2Token(WechatAppTokenVo tokenVo) {
        final WechatAppToken token = new WechatAppToken();
        token.setExpireIn(token.getFetchTime());
        token.setAccessToken(tokenVo.getAccessToken());
        return token;
    }


    @Override
    protected WechatJsApiTicket doFetchJsApiTicket(Merchant merchant, WechatTokenFetchStrategy strategy) {
        final WechatAppToken token = fetchAppToken(merchant, strategy);
        final String tokenStr = token.getAccessToken();
        final WechatJsApiTicketVo ticketVo = wechatApi.getJsApiTicket(tokenStr);
        final WechatJsApiTicket ticket = convert2Ticket(ticketVo);
        ticket.setFetchTime(System.currentTimeMillis());
        ticket.setStrategyId(strategy.getId());
        return ticket;
    }

    private WechatJsApiTicket convert2Ticket(WechatJsApiTicketVo ticketVo) {
        final WechatJsApiTicket ticket = new WechatJsApiTicket();
        ticket.setExpireIn(ticketVo.getRefreshTime());
        ticket.setTicket(ticketVo.getTicket());
        return ticket;
    }
}
