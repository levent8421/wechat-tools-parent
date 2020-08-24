package com.levent8421.wechat.tools.web.commons.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 23:06
 * Class Name: HttpRequestLogInterceptor
 * Author: Levent8421
 * Description:
 * 答应HTTP请求日志
 *
 * @author Levent8421
 */
@Component
@Slf4j
public class HttpRequestLogInterceptor implements HandlerInterceptor {
    private static final String REQUEST_START_TIME = "log.startTime";

    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final long startTime = System.currentTimeMillis();
        request.setAttribute(REQUEST_START_TIME, startTime);

        final String path = request.getRequestURI();
        final String method = request.getMethod();
        log.info("Request: [{}:{}]", method, path);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        final String path = request.getRequestURI();
        final String method = request.getMethod();
        final Long startTime = (Long) request.getAttribute(REQUEST_START_TIME);
        if (startTime == null) {
            log.warn("Could not find startTime in request[{}:{}]", method, path);
            return;
        }
        final long useTime = System.currentTimeMillis() - startTime;

        log.info("Request Done: [{}:{}], use time [{}] ms", method, path, useTime);
    }
}
