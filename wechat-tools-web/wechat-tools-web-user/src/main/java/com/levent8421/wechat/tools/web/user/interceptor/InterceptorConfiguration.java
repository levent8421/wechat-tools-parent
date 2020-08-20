package com.levent8421.wechat.tools.web.user.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:33
 * Class Name: InterceptorConfiguration
 * Author: Levent8421
 * Description:
 * 拦截器配置
 *
 * @author Levent8421
 */
@Configuration
@Component
public class InterceptorConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    private UserTokenInterceptor userTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokenInterceptor);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        userTokenInterceptor = applicationContext.getBean(UserTokenInterceptor.class);
    }
}
