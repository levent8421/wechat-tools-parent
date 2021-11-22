package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:42
 * ClassName: SuperCtlWeather
 * Description:
 * 天气
 *
 * @author levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_super_ctl_weather")
@Data
public class SuperCtlWeather extends AbstractEntity {
    /**
     * 地址
     */
    @Column(name = "address", nullable = false)
    private String address;
    /**
     * 天气简称
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 温度
     */
    @Column(name = "temperature", length = 10)
    private BigDecimal temperature;
    /**
     * 湿度
     */
    @Column(name = "humidity", length = 10)
    private BigDecimal humidity;
    /**
     * 是否有雨
     */
    @Column(name = "in_rain", length = 1, nullable = false)
    private String inRain;
    /**
     * 其他信息
     */
    @Column(name = "ext_info")
    private String extInfo;
    /**
     * 最后一次刷新时间
     */
    @Column(name = "last_refresh_time")
    private String lastRefreshTime;
}
