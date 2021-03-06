package com.levent8421.wechat.tools.web.user.interceptor;

import com.levent8421.wechat.tools.web.commons.interceptor.AbstractTokenInterceptor;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 17:53
 * Class Name: UserTokenInterceptor
 * Author: Levent8421
 * Description:
 * User Token Interceptor
 *
 * @author Levent8421
 */
@Component
public class UserTokenInterceptor extends AbstractTokenInterceptor {
    private final List<String> interceptPathList;
    private final TokenDataHolder tokenDataHolder;
    private final TokenVerifier tokenVerifier;

    public UserTokenInterceptor(TokenDataHolder tokenDataHolder,
                                TokenVerifier tokenVerifier) {
        this.tokenDataHolder = tokenDataHolder;
        this.tokenVerifier = tokenVerifier;
        interceptPathList = new ArrayList<>();
        loadInterceptPath();
    }

    private void loadInterceptPath() {
        interceptPathList.add("/api/token/**");
    }

    @Override
    protected boolean needAuthorization(HttpServletRequest request) {
        final String path = request.getRequestURI();
        for (String reg : interceptPathList) {
            if (matchPath(reg, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected TokenVerifier getTokenVerifier() {
        return tokenVerifier;
    }

    @Override
    protected TokenDataHolder getDataHolder() {
        return tokenDataHolder;
    }
}
