package com.levent8421.wechat.tools.web.commons.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.security.TokenException;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 1:45
 * Class Name: AbstractJwtTokenVerifier
 * Author: Levent8421
 * Description:
 * JWT Token Verifier
 *
 * @author Levent8421
 */
@Component
public abstract class AbstractJwtTokenVerifier implements TokenVerifier {
    private final TokenConfiguration tokenConfiguration;
    private final JWTVerifier verifier;

    public AbstractJwtTokenVerifier(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
        this.verifier = buildVerifier();
    }

    private JWTVerifier buildVerifier() {
        return JWT.require(Algorithm.HMAC256(tokenConfiguration.getKey())).build();
    }

    @Override
    public Map<String, Object> verifyAndDecode(String token) throws TokenException {
        try {
            final DecodedJWT jwt = verifier.verify(token);
            return getPayload(jwt);
        } catch (JWTVerificationException e) {
            throw new TokenException("Verify token error!", e);
        }
    }

    /**
     * Decode Payload data
     *
     * @param jwt jwt
     * @return claims
     */
    protected abstract Map<String, Object> getPayload(DecodedJWT jwt);
}
