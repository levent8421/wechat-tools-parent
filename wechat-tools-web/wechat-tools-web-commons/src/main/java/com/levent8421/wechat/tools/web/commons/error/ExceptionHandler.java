package com.levent8421.wechat.tools.web.commons.error;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 18:16
 * Class Name: ExceptionHandler
 * Author: Levent8421
 * Description:
 * Exception Handler
 *
 * @author Levent8421
 */
public interface ExceptionHandler {
    /**
     * Call On Exception
     *
     * @param error error
     * @return return this object to client
     */
    Object onException(Throwable error);
}
