package com.levent8421.wechat.tools.web.commons.security;

import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 1:39
 * Class Name: TokenVerifier
 * Author: Levent8421
 * Description:
 * Token 验证器
 *
 * @author Levent8421
 */
public interface TokenVerifier {
    /**
     * 验证并解析token
     *
     * @param token token
     * @return token claims
     * @throws TokenException err
     */
    Map<String, Object> verifyAndDecode(String token) throws TokenException;
}
