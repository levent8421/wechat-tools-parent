package com.levent8421.wechat.tools.web.merchant.interceptor;

import com.levent8421.wechat.tools.web.commons.interceptor.HttpRequestLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 1:12
 * Class Name: InterceptorConfiguration
 * Author: Levent8421
 * Description:
 * 拦截器配置组件
 *
 * @author Levent8421
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final MerchantTokenInterceptor merchantTokenInterceptor;
    private final HttpRequestLogInterceptor httpRequestLogInterceptor;

    public InterceptorConfiguration(MerchantTokenInterceptor merchantTokenInterceptor, HttpRequestLogInterceptor httpRequestLogInterceptor) {
        this.merchantTokenInterceptor = merchantTokenInterceptor;
        this.httpRequestLogInterceptor = httpRequestLogInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(merchantTokenInterceptor);
        registry.addInterceptor(httpRequestLogInterceptor);
    }
}
