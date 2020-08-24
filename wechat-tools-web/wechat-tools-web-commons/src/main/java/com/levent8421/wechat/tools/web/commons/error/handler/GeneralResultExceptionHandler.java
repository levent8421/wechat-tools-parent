package com.levent8421.wechat.tools.web.commons.error.handler;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.commons.utils.fn.Function;
import com.levent8421.wechat.tools.web.commons.error.ExceptionHandler;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 18:19
 * Class Name: GeneralResultExceptionHandler
 * Author: Levent8421
 * Description:
 * Exception　Handler implement by GeneralResult
 *
 * @author Levent8421
 */
@Slf4j
public class GeneralResultExceptionHandler implements ExceptionHandler {
    private final Map<Class<?>, Function<Throwable, GeneralResult<?>>> exceptionHandlerTable
            = new HashMap<>(16);
    private final Function<Throwable, GeneralResult<?>> defaultErrorHandler = error -> {
        final String errorMsg = String.format("Error[%s,%s]", error.getClass().getSimpleName(), error.getMessage());
        return GeneralResult.error(errorMsg);
    };

    public GeneralResultExceptionHandler() {
        this.initErrorHandlerTable();
    }

    /**
     * 初始化错误处理表
     */
    private void initErrorHandlerTable() {
        exceptionHandlerTable.put(BadRequestException.class, error -> {
            final String errorMsg = String.format("Error[%s]", error.getMessage());
            return GeneralResult.badRequest(errorMsg);
        });

        exceptionHandlerTable.put(PermissionDeniedException.class, error -> {
            final String errorMsg = String.format("Error[%s]", error.getMessage());
            return GeneralResult.permissionDenied(errorMsg);
        });

        exceptionHandlerTable.put(InternalServerErrorException.class, error -> {
            final String errorMsg = String.format("Error[%s]", error.getMessage());
            return GeneralResult.error(errorMsg);
        });

        exceptionHandlerTable.put(ResourceNotFoundException.class, error -> {
            final String errorMsg = String.format("Error[%s]", error.getMessage());
            return GeneralResult.notFound(errorMsg);
        });
    }

    @Override
    public Object onException(Throwable error) {
        logError(error);
        final Class<?> errorClass = error.getClass();
        final Function<Throwable, GeneralResult<?>> errorHandler
                = exceptionHandlerTable.getOrDefault(errorClass, defaultErrorHandler);
        return errorHandler.exec(error);
    }

    /**
     * Print error log
     *
     * @param error error
     */
    private void logError(Throwable error) {
        final Throwable cause = error.getCause();
        final String causeClass = cause == null ? null : cause.getClass().getSimpleName();
        final String causeMessage = cause == null ? null : cause.getMessage();
        log.warn("Error on resolve http request, error class=[{}],error message=[{}], cause=[{},{}]",
                error.getClass().getSimpleName(), error.getMessage(),
                causeClass, causeMessage, error);
    }
}
