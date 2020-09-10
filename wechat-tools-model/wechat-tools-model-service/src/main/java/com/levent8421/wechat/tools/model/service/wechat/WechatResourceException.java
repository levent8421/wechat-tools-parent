package com.levent8421.wechat.tools.model.service.wechat;

/**
 * Create By leven ont 2020/9/10 22:38
 * Class Name :[WechatResourceException]
 * <p>
 * 微信资源相关异常
 *
 * @author leven
 */
public class WechatResourceException extends Exception {

    public WechatResourceException() {
    }

    public WechatResourceException(String message) {
        super(message);
    }

    public WechatResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatResourceException(Throwable cause) {
        super(cause);
    }

    public WechatResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
