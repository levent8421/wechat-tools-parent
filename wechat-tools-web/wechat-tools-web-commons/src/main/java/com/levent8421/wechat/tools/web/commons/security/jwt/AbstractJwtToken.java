package com.levent8421.wechat.tools.web.commons.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.UUID;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 1:23
 * Class Name: AbstractJwtToken
 * Author: Levent8421
 * Description:
 * JWT Token simple implementation
 *
 * @author Levent8421
 */
public abstract class AbstractJwtToken extends JwtToken {
    private final String key;
    private final long ttl;
    private final Algorithm algorithm;

    public AbstractJwtToken(String key, long ttl) {
        this.key = key;
        this.ttl = ttl;
        this.algorithm = buildSignatureAlgorithm();
    }

    private Algorithm buildSignatureAlgorithm() {
        return Algorithm.HMAC256(key);
    }

    @Override
    Algorithm getSignatureAlgorithm() {
        return algorithm;
    }

    @Override
    long getTtlMillis() {
        return ttl;
    }

    @Override
    String getTokenId() {
        return nextTokenId();
    }

    private String nextTokenId() {
        return UUID.randomUUID().toString();
    }
}
