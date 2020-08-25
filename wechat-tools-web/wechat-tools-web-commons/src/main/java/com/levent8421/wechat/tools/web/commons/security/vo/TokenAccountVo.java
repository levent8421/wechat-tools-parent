package com.levent8421.wechat.tools.web.commons.security.vo;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 23:30
 * Class Name: TokenAccountVo
 * Author: Levent8421
 * Description:
 * 携带token和account信息的value object
 *
 * @author Levent8421
 */
@Data
public class TokenAccountVo<T> {
    /**
     * Token string
     */
    private String token;
    /**
     * Account object
     */
    private T account;
}
