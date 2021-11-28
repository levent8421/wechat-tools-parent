package com.levent8421.wechat.tools.weather.impl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/11/28 19:16
 * ClassName: ZhiXinWeatherResult
 * Description:
 * 知心天气结果
 *
 * @author levent8421
 */
@Data
public class ZhiXinWeatherResult {
    private String text;
    private String code;
    private String temperature;
    @JSONField(name = "feels_like")
    private String feelsLike;
    private String pressure;
    private String humidity;
    private String visibility;
    @JSONField(name = "wind_direction")
    private String windDirection;
    @JSONField(name = "wind_direction_degree")
    private String windDirectionDegree;
    @JSONField(name = "windSpeed")
    private String windSpeed;
    @JSONField(name = "wind_scale")
    private String windScale;
    private String clouds;
    @JSONField(name = "dew_point")
    private String dewPoint;
}
