package com.levent8421.wechat.tools.message.mqtt.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by Levent8421
 * Date: 2021/11/25 13:49
 * ClassName: MessageConf
 * Description:
 * Message Config
 *
 * @author levent8421
 */
@Component
@ConfigurationProperties(prefix = "message")
@Data
public class MessageConf {
    /**
     * MQ服务器
     */
    private String broker;
    /**
     * uname
     */
    private String username;
    /**
     * passwd
     */
    private String password;
    /**
     * Client Id
     */
    private String clientId;
    private boolean autoReconnect = true;
    private int keepAlive = 20;
    private int connectTimeout = 10;
}
