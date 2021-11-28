package com.levent8421.wechat.tools.weather;

/**
 * Create by Levent8421
 * Date: 2021/11/28 18:45
 * ClassName: WeatherApi
 * Description:
 * 天气API
 *
 * @author levent8421
 */
public interface WeatherApi {
    /**
     * 获取某地区的天气
     *
     * @param address 地址
     * @return 天气
     * @throws WeatherException Error
     */
    WeatherInfo fetchWeather(String address) throws WeatherException;
}
