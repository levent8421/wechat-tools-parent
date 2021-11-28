package com.levent8421.wechat.tools.weather.impl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by Levent8421
 * Date: 2021/11/28 19:05
 * ClassName: ZhiXinWeatherConfig
 * Description:
 * 知心天气接口配置
 *
 * @author levent8421
 */
@Data
@Component
@ConfigurationProperties(prefix = "zhixin")
public class ZhiXinWeatherConfig {
    private String apiKey;
}
