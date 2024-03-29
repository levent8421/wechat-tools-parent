package com.levent8421.wechat.tools.web.user.serurity;

import com.auth0.jwt.JWTCreator;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtToken;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:07
 * Class Name: UserToken
 * Author: Levent8421
 * Description:
 * 用户令牌
 *
 * @author Levent8421
 */
public class UserToken extends AbstractJwtToken {
    public static final long DEFAULT_TTL = 24 * 60 * 60 * 1000;
    public static final String MERCHANT_ID_NAME = "userToken.merchantId";
    public static final String USER_ID_NAME = "userToken.userId";

    public static Integer obtainUid(TokenDataHolder holder) {
        return holder.get(USER_ID_NAME, Integer.class);
    }

    private final WechatUser user;

    public UserToken(WechatUser user, String key, long ttl) {
        super(key, ttl);
        this.user = user;
    }

    public UserToken(WechatUser user, String key) {
        super(key, DEFAULT_TTL);
        this.user = user;
    }

    @Override
    protected void initClaims(JWTCreator.Builder builder) {
        builder.withClaim(UserToken.USER_ID_NAME, user.getId())
                .withClaim(UserToken.MERCHANT_ID_NAME, user.getMerchantId());
    }
}
