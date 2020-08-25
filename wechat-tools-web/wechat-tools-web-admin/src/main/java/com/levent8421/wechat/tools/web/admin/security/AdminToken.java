package com.levent8421.wechat.tools.web.admin.security;

import com.auth0.jwt.JWTCreator;
import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtToken;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 0:52
 * Class Name: AdminToken
 * Author: Levent8421
 * Description:
 * 管理员令牌
 *
 * @author Levent8421
 */
public class AdminToken extends AbstractJwtToken {
    /**
     * 默认令牌有效时间 1天
     */
    private static final long DEFAULT_TTL = 24 * 60 * 60 * 1000;
    public static final String ADMIN_ID_NAME = "adminToken.id";
    public static final String ADMIN_LOGIN_NAME_NAME = "adminToken.loginName";
    public static final String ADMIN_NAME_NAME = "adminToken.name";
    private Admin admin;

    public AdminToken(Admin admin, String key) {
        super(key, DEFAULT_TTL);
        this.admin = admin;
    }

    public AdminToken(Admin admin, String key, long ttl) {
        super(key, ttl);
        this.admin = admin;
    }

    @Override
    protected void initClaims(JWTCreator.Builder builder) {
        builder.withClaim(ADMIN_ID_NAME, admin.getId());
        builder.withClaim(ADMIN_NAME_NAME, admin.getName());
        builder.withClaim(ADMIN_LOGIN_NAME_NAME, admin.getLoginName());
    }
}
