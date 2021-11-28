package com.levent8421.wechat.tools.web.user.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * Create by Levent8421
 * Date: 2021/11/26 15:12
 * ClassName: WebsocketConfig
 * Description:
 * Websocket Config
 *
 * @author levent8421
 */
@Configuration
public class WebsocketConfig {
    /**
     * WebSocket endpoint
     *
     * @return endpoint exporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketSpringConfigurator webSocketSpringConfigurator() {
        return new WebSocketSpringConfigurator();
    }
}
