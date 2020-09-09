package com.levent8421.wechat.tools.web.merchant.interceptor;

import com.levent8421.wechat.tools.web.commons.interceptor.AbstractTokenInterceptor;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import com.levent8421.wechat.tools.web.merchant.security.MerchantTokenVerifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/9 16:16
 * Class Name: MerchantTokenInterceptor
 * Author: Levent8421
 * Description:
 * 商户令牌拦截器
 *
 * @author Levent8421
 */
@Component
public class MerchantTokenInterceptor extends AbstractTokenInterceptor {
    private final TokenDataHolder tokenDataHolder;
    private final MerchantTokenVerifier merchantTokenVerifier;
    private final List<String> interceptPathList;

    public MerchantTokenInterceptor(TokenDataHolder tokenDataHolder,
                                    MerchantTokenVerifier merchantTokenVerifier) {
        this.tokenDataHolder = tokenDataHolder;
        this.merchantTokenVerifier = merchantTokenVerifier;
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
        return merchantTokenVerifier;
    }

    @Override
    protected TokenDataHolder getDataHolder() {
        return tokenDataHolder;
    }
}
