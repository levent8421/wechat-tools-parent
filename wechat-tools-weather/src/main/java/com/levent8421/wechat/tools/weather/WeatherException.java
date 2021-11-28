package com.levent8421.wechat.tools.weather;

/**
 * Create by Levent8421
 * Date: 2021/11/28 19:23
 * ClassName: WeatherException
 * Description:
 * 天气异常
 *
 * @author levent8421
 */
public class WeatherException extends Exception {
    public WeatherException() {
    }

    public WeatherException(String message) {
        super(message);
    }

    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherException(Throwable cause) {
        super(cause);
    }

    public WeatherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
