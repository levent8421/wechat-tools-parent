package com.levent8421.wechat.tools.web.commons.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 1:24
 * Class Name: TokenConfiguration
 * Author: Levent8421
 * Description:
 * Web Token Configuration
 *
 * @author Levent8421
 */
@Component
@Data
@ConfigurationProperties(prefix = "web-token")
public class TokenConfiguration {
    /**
     * 令牌有效时间
     */
    private long ttl;
    /**
     * 签名密钥
     */
    private String key;
}
