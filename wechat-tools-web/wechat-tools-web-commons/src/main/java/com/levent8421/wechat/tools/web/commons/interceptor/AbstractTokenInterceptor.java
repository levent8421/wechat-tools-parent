package com.levent8421.wechat.tools.web.commons.interceptor;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.security.TokenException;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 12:17
 * Class Name: AbstractTokenInterceptor
 * Author: Levent8421
 * Description:
 * Token Interceptor
 *
 * @author Levent8421
 */
@Slf4j
public abstract class AbstractTokenInterceptor implements HandlerInterceptor {
    private static final String TOKEN_PARAM_NAME = "token";
    private static final String TOKEN_HEADER_NAME = "X-Token";

    private String tryFindToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER_NAME);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_PARAM_NAME);
        }
        return token;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!needAuthorization(request)) {
            return true;
        }
        try {
            doInterceptor(request);
            return true;
        } catch (Exception e) {
            writeErrorData(response, e);
            log.warn("PermissionDenied [{}],[{},{}]", request.getRequestURI(),
                    e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    /**
     * 判断请求是否需要授权
     *
     * @param request request
     * @return need?
     */
    protected abstract boolean needAuthorization(HttpServletRequest request);

    /**
     * 获取Token验证器
     *
     * @return verifier
     */
    protected abstract TokenVerifier getTokenVerifier();

    /**
     * 获取数据保持器
     *
     * @return holder
     */
    protected abstract TokenDataHolder getDataHolder();

    private void doInterceptor(HttpServletRequest request) throws TokenException {
        final String token = tryFindToken(request);
        if (StringUtils.isBlank(token)) {
            throw new PermissionDeniedException("No Token!");
        }
        final TokenVerifier verifier = getTokenVerifier();
        final Map<String, Object> claims = verifier.verifyAndDecode(token);
        getDataHolder().putAll(claims);
    }

    private void writeErrorData(HttpServletResponse response, Throwable error) {
        final String errorMessage = String.format("Error [%s]:%s", error.getClass().getSimpleName(), error.getMessage());
        final GeneralResult res = GeneralResult.permissionDenied(errorMessage);
        final String errorJson = JSON.toJSONString(res);
        try {
            final PrintWriter writer = response.getWriter();
            writer.write(errorJson);
            writer.flush();
        } catch (IOException e) {
            log.warn("Error on write error message to http response", e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        getDataHolder().clearData();
    }
}
