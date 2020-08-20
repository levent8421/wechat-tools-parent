package com.levent8421.wechat.tools.web.user.serurity;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtTokenVerifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:05
 * Class Name: UserTokenVerifier
 * Author: Levent8421
 * Description:
 * 用户Token验证器
 *
 * @author Levent8421
 */
@Component
public class UserTokenVerifier extends AbstractJwtTokenVerifier {

    public UserTokenVerifier(TokenConfiguration tokenConfiguration) {
        super(tokenConfiguration);
    }

    @Override
    protected Map<String, Object> getPayload(DecodedJWT jwt) {
        final Map<String, Object> claims = new HashMap<>(16);
        final Claim userId = jwt.getClaim(UserToken.USER_ID_NAME);
        claims.put(UserToken.USER_ID_NAME, userId.asInt());
        final Claim merchantId = jwt.getClaim(UserToken.MERCHANT_ID_NAME);
        claims.put(UserToken.MERCHANT_ID_NAME, merchantId.asInt());
        return claims;
    }
}
