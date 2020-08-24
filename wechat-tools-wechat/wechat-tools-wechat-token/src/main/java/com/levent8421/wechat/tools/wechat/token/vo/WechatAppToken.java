package com.levent8421.wechat.tools.wechat.token.vo;

import com.levent8421.wechat.tools.cache.Cacheable;
import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/8/22 15:15
 * Class Name: WechatAppToken
 * Author: Levent8421
 * Description:
 * 微信APP token value object
 *
 * @author Levent8421
 */
@Data
public class WechatAppToken implements Cacheable {
    /**
     * 抓取策略ID
     */
    private Integer strategyId;
    /**
     * Token String
     */
    private String accessToken;
    /**
     * 过期时间
     */
    private Long expireIn;
    /**
     * 该令牌的获取时间
     */
    private long fetchTime;

    @Override
    public String getKey() {
        return String.valueOf(strategyId);
    }
}
