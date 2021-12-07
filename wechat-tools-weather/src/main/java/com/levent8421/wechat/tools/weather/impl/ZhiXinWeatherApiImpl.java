package com.levent8421.wechat.tools.weather.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.levent8421.wechat.tools.commons.http.AbstractHttpApi;
import com.levent8421.wechat.tools.commons.utils.datetime.DateTimeUtils;
import com.levent8421.wechat.tools.weather.WeatherApi;
import com.levent8421.wechat.tools.weather.WeatherException;
import com.levent8421.wechat.tools.weather.WeatherInfo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/28 19:03
 * ClassName: ZhiXinWeatherApiImpl
 * Description:
 * 知心天气接口
 * <code>
 * <pre>
 *     {
 *     "results": [
 *         {
 *             "location": {
 *                 "id": "WX4FBXXFKE4F",
 *                 "name": "北京",
 *                 "country": "CN",
 *                 "path": "北京,北京,中国",
 *                 "timezone": "Asia/Shanghai",
 *                 "timezone_offset": "+08:00"
 *             },
 *             "now": {
 *                 "text": "霾",
 *                 "code": "31",
 *                 "temperature": "5",
 *                 "feels_like": "4",
 *                 "pressure": "1022",
 *                 "humidity": "75",
 *                 "visibility": "2.2",
 *                 "wind_direction": "西南",
 *                 "wind_direction_degree": "242",
 *                 "wind_speed": "1.0",
 *                 "wind_scale": "0",
 *                 "clouds": "5",
 *                 "dew_point": ""
 *             },
 *             "last_update": "2021-11-28T18:11:33+08:00"
 *         }
 *     ]
 * }
 * </pre>
 * </code>
 *
 * @author levent8421
 */
@Component
public class ZhiXinWeatherApiImpl extends AbstractHttpApi implements WeatherApi {
    private static final String API_URL = "https://api.seniverse.com/v3/weather/now.json";
    private final ZhiXinWeatherConfig config;

    public ZhiXinWeatherApiImpl(ZhiXinWeatherConfig config) {
        this.config = config;
    }

    @Override
    public WeatherInfo fetchWeather(String address) throws WeatherException {
        String apiKey = config.getApiKey();
        Map<String, String> params = Maps.newHashMap();
        params.put("key", apiKey);
        params.put("location", address);
        params.put("language", "zh-Hans");
        params.put("unit", "c");
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value;
            try {
                value = URLEncoder.encode(entry.getValue(), "utf8");
            } catch (UnsupportedEncodingException e) {
                throw new WeatherException(ExceptionUtils.getMessage(e), e);
            }
            query.append(entry.getKey())
                    .append('=')
                    .append(value)
                    .append('&');
        }
        String url = API_URL + '?' + query;
        String respStr = get(url);
        JSONObject resp = JSON.parseObject(respStr);
        JSONArray results = resp.getJSONArray("results");
        if (results == null || results.size() < 1) {
            String errorCode = resp.getString("status_code");
            String error = resp.getString("status");
            throw new WeatherException(errorCode + "|" + error);
        }
        ZhiXinWeatherResult result = results.getObject(0, ZhiXinWeatherResult.class);
        WeatherInfo res = new WeatherInfo();
        res.setAddress(address);
        res.setTitle(result.getText());
        res.setTemperature(new BigDecimal(result.getTemperature()));
        res.setHumidity(new BigDecimal(result.getHumidity()));
        res.setExtraInfo(respStr);
        res.setRefreshTime(DateTimeUtils.now());
        int code = Integer.parseInt(result.getCode());
        ZhiXinWeatherCode weatherCode = ZhiXinWeatherCode.get(code);
        res.setInRain(weatherCode != null && weatherCode.isInRain());
        return res;
    }
}
