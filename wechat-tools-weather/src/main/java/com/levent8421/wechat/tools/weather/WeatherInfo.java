package com.levent8421.wechat.tools.weather;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/28 18:55
 * ClassName: WeatherInfo
 * Description:
 * 天气信息
 *
 * @author levent8421
 */
@Data
public class WeatherInfo {
    private String address;
    private String title;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private boolean inRain;
    private String extraInfo;
    private Date refreshTime;
}
