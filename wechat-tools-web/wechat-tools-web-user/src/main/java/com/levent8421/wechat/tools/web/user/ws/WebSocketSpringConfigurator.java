package com.levent8421.wechat.tools.web.user.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Create by Levent8421
 * Date: 2021/11/28 16:30
 * ClassName: WebSocketSpringConfigurator
 * Description:
 * Websocket Spring Configurator
 *
 * @author levent8421
 */
public class WebSocketSpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSocketSpringConfigurator.applicationContext = applicationContext;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return applicationContext.getBean(clazz);
    }
}
