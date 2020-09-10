package com.levent8421.wechat.tools.model.service.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create By leven ont 2020/9/10 22:26
 * Class Name :[WebsiteConfigurationProperties]
 * <p>
 * 网站相关配置参数
 *
 * @author leven
 */
@Slf4j
@Data
@Component
@ConfigurationProperties("website")
public class WebsiteConfigurationProperties {
    @PostConstruct
    public void showWebsiteBaseUrl() {
        log.info("Starting application at [{}]", baseUrl);
    }

    /**
     * 网站基础路径
     * http://wt.levent8421.com
     */
    private String baseUrl;
}
