package com.levent8421.wechat.tools.message;

/**
 * Create by Levent8421
 * Date: 2021/11/25 17:11
 * ClassName: MessageException
 * Description:
 * Message Exception
 *
 * @author levent8421
 */
public class MessageException extends Exception {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
