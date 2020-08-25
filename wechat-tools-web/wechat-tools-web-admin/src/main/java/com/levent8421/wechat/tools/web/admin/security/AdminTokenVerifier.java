package com.levent8421.wechat.tools.web.admin.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtTokenVerifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 0:56
 * Class Name: AdminTokenVerifier
 * Author: Levent8421
 * Description:
 * 管理员令牌验证器
 *
 * @author Levent8421
 */
@Component
public class AdminTokenVerifier extends AbstractJwtTokenVerifier {
    public AdminTokenVerifier(TokenConfiguration tokenConfiguration) {
        super(tokenConfiguration);
    }

    @Override
    protected Map<String, Object> getPayload(DecodedJWT jwt) {
        final Integer adminId = jwt.getClaim(AdminToken.ADMIN_ID_NAME).asInt();
        final String adminName = jwt.getClaim(AdminToken.ADMIN_NAME_NAME).asString();
        final String adminLoginName = jwt.getClaim(AdminToken.ADMIN_LOGIN_NAME_NAME).asString();

        final Map<String, Object> payload = new HashMap<>(16);
        payload.put(AdminToken.ADMIN_ID_NAME, adminId);
        payload.put(AdminToken.ADMIN_NAME_NAME, adminName);
        payload.put(AdminToken.ADMIN_LOGIN_NAME_NAME, adminLoginName);
        return payload;
    }
}
