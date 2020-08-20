package com.levent8421.wechat.tools.web.commons.security;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 1:42
 * Class Name: TokenException
 * Author: Levent8421
 * Description:
 * Token Exception
 *
 * @author Levent8421
 */
public class TokenException extends Exception {
    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenException(Throwable cause) {
        super(cause);
    }

    public TokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
