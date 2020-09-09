package com.levent8421.wechat.tools.web.merchant.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.security.jwt.AbstractJwtTokenVerifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/9/9 16:13
 * Class Name: MerchantTokenVerifier
 * Author: Levent8421
 * Description:
 * 商户登录令牌校验器
 *
 * @author Levent8421
 */
@Component
public class MerchantTokenVerifier extends AbstractJwtTokenVerifier {
    public MerchantTokenVerifier(TokenConfiguration tokenConfiguration) {
        super(tokenConfiguration);
    }

    @Override
    protected Map<String, Object> getPayload(DecodedJWT jwt) {
        final Map<String, Object> payload = new HashMap<>(16);
        final Integer merchantId = jwt.getClaim(MerchantToken.MERCHANT_ID_KEY).asInt();
        final String merchantName = jwt.getClaim(MerchantToken.MERCHANT_NAME_KEY).asString();
        final String merchantAuthorizationCode = jwt.getClaim(MerchantToken.MERCHANT_AUTHORIZATION_KEY).asString();

        payload.put(MerchantToken.MERCHANT_ID_KEY, merchantId);
        payload.put(MerchantToken.MERCHANT_NAME_KEY, merchantName);
        payload.put(MerchantToken.MERCHANT_AUTHORIZATION_KEY, merchantAuthorizationCode);
        return payload;
    }
}
