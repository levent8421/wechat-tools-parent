package com.levent8421.wechat.tools.model.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 17:50
 * Class Name: WechatConfigurationProperties
 * Author: Levent8421
 * Description:
 * 微信相关配置参数
 *
 * @author Levent8421
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfigurationProperties {
    /**
     * 服务器IP
     * 需要配置再微信公众平台白名单中
     */
    private String serverIp;
    /**
     * JS API的安全域名
     */
    private String jsApiDomain;
    /**
     * 微信授权安全域名
     */
    private String authorizationDomain;
}
