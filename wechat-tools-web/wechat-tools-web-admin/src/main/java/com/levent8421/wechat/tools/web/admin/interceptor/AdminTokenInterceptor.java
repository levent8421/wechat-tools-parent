package com.levent8421.wechat.tools.web.admin.interceptor;

import com.levent8421.wechat.tools.web.admin.security.AdminTokenVerifier;
import com.levent8421.wechat.tools.web.commons.interceptor.AbstractTokenInterceptor;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 1:06
 * Class Name: AdminTokenInterceptor
 * Author: Levent8421
 * Description:
 * 管理员令牌拦截器
 *
 * @author Levent8421
 */
@Component
public class AdminTokenInterceptor extends AbstractTokenInterceptor {
    private final TokenDataHolder tokenDataHolder;
    private final AdminTokenVerifier adminTokenVerifier;
    private final List<String> interceptPathList;

    public AdminTokenInterceptor(TokenDataHolder tokenDataHolder,
                                 AdminTokenVerifier adminTokenVerifier) {
        this.tokenDataHolder = tokenDataHolder;
        this.adminTokenVerifier = adminTokenVerifier;
        interceptPathList = new ArrayList<>();
        loadPathList();
    }

    private void loadPathList() {
        interceptPathList.add("/api/token/**");
    }

    @Override
    protected boolean needAuthorization(HttpServletRequest request) {
        final String uri = request.getRequestURI();
        for (String path : interceptPathList) {
            if (matchPath(path, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected TokenVerifier getTokenVerifier() {
        return adminTokenVerifier;
    }

    @Override
    protected TokenDataHolder getDataHolder() {
        return tokenDataHolder;
    }
}
