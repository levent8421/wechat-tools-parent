package com.levent8421.wechat.tools.web.merchant.security;

import com.auth0.jwt.JWTCreator;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtToken;

/**
 * Create By Levent8421
 * Create Time: 2020/9/9 16:09
 * Class Name: MerchantToken
 * Author: Levent8421
 * Description:
 * 商户登录token
 *
 * @author Levent8421
 */
public class MerchantToken extends AbstractJwtToken {
    /**
     * 默认令牌有效时间 1天
     */
    private static final long DEFAULT_TTL = 24 * 60 * 60 * 1000;
    public static final String MERCHANT_ID_KEY = "m.id";
    public static final String MERCHANT_AUTHORIZATION_KEY = "m.auth";
    public static final String MERCHANT_NAME_KEY = "m.name";
    private final Merchant merchant;

    public MerchantToken(String key, Merchant merchant) {
        super(key, DEFAULT_TTL);
        this.merchant = merchant;
    }

    @Override
    protected void initClaims(JWTCreator.Builder builder) {
        builder.withClaim(MERCHANT_ID_KEY, merchant.getId())
                .withClaim(MERCHANT_NAME_KEY, merchant.getName())
                .withClaim(MERCHANT_AUTHORIZATION_KEY, merchant.getAuthorizationCode());
    }
}
