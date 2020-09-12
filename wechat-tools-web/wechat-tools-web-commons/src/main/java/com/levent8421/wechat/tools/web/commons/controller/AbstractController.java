package com.levent8421.wechat.tools.web.commons.controller;

import com.levent8421.wechat.tools.web.commons.error.ExceptionHandler;
import com.levent8421.wechat.tools.web.commons.error.handler.GeneralResultExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 17:51
 * Class Name: AbstractController
 * Author: Levent8421
 * Description:
 * Abstract Controller
 *
 * @author Levent8421
 */
public abstract class AbstractController {
    private final ExceptionHandler exceptionHandler;

    public AbstractController() {
        this.exceptionHandler = getExceptionHandler();
    }

    /**
     * Get Default Exception Handler
     *
     * @return exception handler
     */
    protected ExceptionHandler getExceptionHandler() {
        return new GeneralResultExceptionHandler();
    }

    /**
     * 处理异常
     *
     * @param error error exception
     * @return view object
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public Object onAnyException(Throwable error) {
        return exceptionHandler.onException(error);
    }

    /**
     * 重定向视图名称
     *
     * @param url 视图
     * @return view name
     */
    protected String redirect(String url) {
        return String.format("redirect:%s", url);
    }

    /**
     * 重定向视图
     *
     * @param url url
     * @return MV
     */
    protected ModelAndView redirectView(String url) {
        final String view = redirect(url);
        return new ModelAndView(view);
    }
}
