package com.levent8421.wechat.tools.wechat.token.vo;

import com.levent8421.wechat.tools.cache.Cacheable;
import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/8/22 15:23
 * Class Name: WechatJsApiTicket
 * Author: Levent8421
 * Description:
 * 微信 JS API Ticket
 *
 * @author Levent8421
 */
@Data
public class WechatJsApiTicket implements Cacheable {
    /**
     * 抓取策略ID
     */
    private Integer strategyId;
    /**
     * Ticket string
     */
    private String ticket;
    /**
     * Expire  time
     */
    private Long expireIn;
    /**
     * 令牌获取时间
     */
    private long fetchTime;

    @Override
    public String getKey() {
        return String.valueOf(strategyId);
    }
}
