package com.levent8421.wechat.tools.web.commons.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.levent8421.wechat.tools.web.commons.security.WebToken;

import java.util.Date;

/**
 * Create By Levent8421
 * Create Time: 2020/8/19 23:49
 * Class Name: JwtToken
 * Author: Levent8421
 * Description:
 * JWT Token
 *
 * @author Levent8421
 */
public abstract class JwtToken implements WebToken {
    /**
     * Load Claims
     *
     * @param builder jwt builder
     */
    protected abstract void initClaims(JWTCreator.Builder builder);

    /**
     * 获取签名算法
     *
     * @return 签名算法
     */
    abstract Algorithm getSignatureAlgorithm();

    /**
     * 获取过期时间
     *
     * @return 过期时间（毫秒）
     */
    abstract long getTtlMillis();

    /**
     * 获取TokenId
     *
     * @return token id
     */
    abstract String getTokenId();

    @Override
    public String toTokenString() {
        return buildJwt();
    }

    /**
     * Build a jwt token string
     *
     * @return token string
     */
    private String buildJwt() {
        final Date now = new Date();
        final JWTCreator.Builder builder = JWT.create();
        initClaims(builder);
        return builder.withJWTId(getTokenId())
                .withIssuedAt(now)
                .withExpiresAt(getExpireDate(now, getTtlMillis()))
                .sign(getSignatureAlgorithm());
    }

    /**
     * 计算token过期时间
     *
     * @param now       now
     * @param ttlMillis 生存时间
     * @return Date
     */
    private Date getExpireDate(Date now, long ttlMillis) {
        return new Date(now.getTime() + ttlMillis);
    }
}
