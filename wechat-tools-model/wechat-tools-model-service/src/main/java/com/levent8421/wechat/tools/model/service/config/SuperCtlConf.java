package com.levent8421.wechat.tools.model.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by Levent8421
 * Date: 2021/11/25 17:52
 * ClassName: SuperCtlConf
 * Description:
 * Super Ctl Conf
 *
 * @author levent8421
 */
@Component
@ConfigurationProperties(prefix = "super-ctl")
@Data
public class SuperCtlConf {
    /**
     * 上行Topic
     */
    private String superCtlUpstreamTopic;
    /**
     * 下行Topic前缀
     */
    private String superCtlDownstreamTopicPrefix;
}
