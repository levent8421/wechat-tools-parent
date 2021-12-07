package com.levent8421.wechat.tools.web.user.task;

import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.service.general.SuperCtlWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/29 16:41
 * ClassName: WeatherRefreshTask
 * Description:
 * 天气刷新任务
 *
 * @author levent8421
 */
@Slf4j
@Component
public class WeatherRefreshTask {
    private static final int MAX_REFRESH_SIZE = 100;
    private final SuperCtlWeatherService superCtlWeatherService;

    public WeatherRefreshTask(SuperCtlWeatherService superCtlWeatherService) {
        this.superCtlWeatherService = superCtlWeatherService;
    }

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void weatherRefresh() {
        List<SuperCtlWeather> weathers = superCtlWeatherService.findNeedRefreshRecord(MAX_REFRESH_SIZE);
        for (SuperCtlWeather weather : weathers) {
            try {
                refreshWeather(weather);
            } catch (Exception e) {
                log.error("Error on refresh weather:[{}]", weather.getId(), e);
            }
        }
    }

    private void refreshWeather(SuperCtlWeather weather) {
        superCtlWeatherService.refresh(weather);
    }
}
