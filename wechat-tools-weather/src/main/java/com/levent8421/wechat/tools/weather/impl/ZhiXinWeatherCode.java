package com.levent8421.wechat.tools.weather.impl;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/28 19:33
 * ClassName: ZhiXinWeatherCode
 * Description:
 * 知心天气代码
 *
 * @author levent8421
 */
@Data
public class ZhiXinWeatherCode {
    private static final Map<Integer, ZhiXinWeatherCode> CODE_TABLE;

    static {
        CODE_TABLE = Maps.newHashMap();
        CODE_TABLE.put(0, new ZhiXinWeatherCode(0, "晴（国内城市白天晴）", "Sunny", false));
        CODE_TABLE.put(1, new ZhiXinWeatherCode(1, "晴（国内城市夜晚晴）", "Clear", false));
        CODE_TABLE.put(2, new ZhiXinWeatherCode(2, "晴（国外城市白天晴）", "Fair", false));
        CODE_TABLE.put(3, new ZhiXinWeatherCode(3, "晴（国外城市夜晚晴）", "Fair", false));
        CODE_TABLE.put(4, new ZhiXinWeatherCode(4, "多云", "Cloudy", false));
        CODE_TABLE.put(5, new ZhiXinWeatherCode(5, "晴间多云", "PartlyCloudy", false));
        CODE_TABLE.put(6, new ZhiXinWeatherCode(6, "晴间多云", "PartlyCloudy", false));
        CODE_TABLE.put(7, new ZhiXinWeatherCode(7, "大部多云", "MostlyCloudy", false));
        CODE_TABLE.put(8, new ZhiXinWeatherCode(8, "大部多云", "MostlyCloudy", false));
        CODE_TABLE.put(9, new ZhiXinWeatherCode(9, "阴", "Overcast", false));
        CODE_TABLE.put(10, new ZhiXinWeatherCode(10, "阵雨", "Shower", true));
        CODE_TABLE.put(11, new ZhiXinWeatherCode(11, "雷阵雨", "Thundershower", true));
        CODE_TABLE.put(12, new ZhiXinWeatherCode(12, "雷阵雨伴有冰雹", "ThundershowerwithHail", true));
        CODE_TABLE.put(13, new ZhiXinWeatherCode(13, "小雨", "LightRain", true));
        CODE_TABLE.put(14, new ZhiXinWeatherCode(14, "中雨", "ModerateRain", true));
        CODE_TABLE.put(15, new ZhiXinWeatherCode(15, "大雨", "HeavyRain", true));
        CODE_TABLE.put(16, new ZhiXinWeatherCode(16, "暴雨", "Storm", true));
        CODE_TABLE.put(17, new ZhiXinWeatherCode(17, "大暴雨", "HeavyStorm", true));
        CODE_TABLE.put(18, new ZhiXinWeatherCode(18, "特大暴雨", "SevereStorm", true));
        CODE_TABLE.put(19, new ZhiXinWeatherCode(19, "冻雨", "IceRain", true));
        CODE_TABLE.put(20, new ZhiXinWeatherCode(20, "雨夹雪", "Sleet", true));
        CODE_TABLE.put(21, new ZhiXinWeatherCode(21, "阵雪", "SnowFlurry", true));
        CODE_TABLE.put(22, new ZhiXinWeatherCode(22, "小雪", "LightSnow", true));
        CODE_TABLE.put(23, new ZhiXinWeatherCode(23, "中雪", "ModerateSnow", true));
        CODE_TABLE.put(24, new ZhiXinWeatherCode(24, "大雪", "HeavySnow", true));
        CODE_TABLE.put(25, new ZhiXinWeatherCode(25, "暴雪", "Snowstorm", true));
        CODE_TABLE.put(26, new ZhiXinWeatherCode(26, "浮尘", "Dust", false));
        CODE_TABLE.put(27, new ZhiXinWeatherCode(27, "扬沙", "Sand", false));
        CODE_TABLE.put(28, new ZhiXinWeatherCode(28, "沙尘暴", "Duststorm", false));
        CODE_TABLE.put(29, new ZhiXinWeatherCode(29, "强沙尘暴", "Sandstorm", false));
        CODE_TABLE.put(30, new ZhiXinWeatherCode(30, "雾", "Foggy", false));
        CODE_TABLE.put(31, new ZhiXinWeatherCode(31, "霾", "Haze", false));
        CODE_TABLE.put(32, new ZhiXinWeatherCode(32, "风", "Windy", false));
        CODE_TABLE.put(33, new ZhiXinWeatherCode(33, "大风", "Blustery", false));
        CODE_TABLE.put(34, new ZhiXinWeatherCode(34, "飓风", "Hurricane", false));
        CODE_TABLE.put(35, new ZhiXinWeatherCode(35, "热带风暴", "TropicalStorm", false));
        CODE_TABLE.put(36, new ZhiXinWeatherCode(36, "龙卷风", "Tornado", false));
        CODE_TABLE.put(37, new ZhiXinWeatherCode(37, "冷", "Cold", false));
        CODE_TABLE.put(38, new ZhiXinWeatherCode(38, "热", "Hot", false));
        CODE_TABLE.put(99, new ZhiXinWeatherCode(99, "未知", "Unknown", false));
    }

    public static ZhiXinWeatherCode get(int code) {
        return CODE_TABLE.get(code);
    }

    private final int code;
    private final String name;
    private final String englishName;
    private final boolean inRain;

    private ZhiXinWeatherCode(int code, String name, String englishName, boolean inRain) {
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.inRain = inRain;
    }
}
