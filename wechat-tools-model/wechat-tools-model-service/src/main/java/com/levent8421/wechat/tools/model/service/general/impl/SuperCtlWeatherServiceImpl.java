package com.levent8421.wechat.tools.model.service.general.impl;

import com.google.common.collect.Maps;
import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.utils.datetime.DateTimeUtils;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlWeatherMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlWeatherService;
import com.levent8421.wechat.tools.weather.WeatherApi;
import com.levent8421.wechat.tools.weather.WeatherException;
import com.levent8421.wechat.tools.weather.WeatherInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlDeviceServiceImpl Class
 *
 * @author levent8421
 */
@Slf4j
@Service
public class SuperCtlWeatherServiceImpl extends AbstractServiceImpl<SuperCtlWeather> implements SuperCtlWeatherService {

    private static final int EXPIRE_MS = 6 * 60 * 60 * 1000;
    private final SuperCtlWeatherMapper superCtlWeatherMapper;
    private final WeatherApi weatherApi;

    public SuperCtlWeatherServiceImpl(SuperCtlWeatherMapper mapper, WeatherApi weatherApi) {
        super(mapper);
        this.superCtlWeatherMapper = mapper;
        this.weatherApi = weatherApi;
    }

    @Override
    public int markRefresh(List<Integer> ids, boolean needRefresh) {
        return superCtlWeatherMapper.updateNeedRefresh(ids, needRefresh);
    }

    @Override
    public List<SuperCtlWeather> findNeedRefreshRecord(int max) {
        return superCtlWeatherMapper.selectByNeedRefresh(max, true);
    }

    @Override
    public void refresh(SuperCtlWeather weather) {
        refreshWeather(weather);
        updateById(weather);
    }

    @Override
    public Map<String, SuperCtlWeather> getWeathers(Set<String> addressArr) {
        Map<String, SuperCtlWeather> dbWeathers = findWeathers(addressArr);
        List<String> notExistsAddressArr = Lists.newArrayList();
        for (String address : addressArr) {
            if (!dbWeathers.containsKey(address)) {
                notExistsAddressArr.add(address);
            }
        }
        List<Integer> expiredIds = Lists.newArrayList();
        for (SuperCtlWeather weather : dbWeathers.values()) {
            if (!isExpired(weather)) {
                continue;
            }
            if (weather.isNeedRefresh()) {
                continue;
            }
            expiredIds.add(weather.getId());
        }
        if (expiredIds.size() > 0) {
            markRefresh(expiredIds, true);
        }
        List<SuperCtlWeather> weathersToSave = Lists.newArrayList();
        for (String address : notExistsAddressArr) {
            try {
                SuperCtlWeather weather = new SuperCtlWeather();
                weather.setAddress(address);
                refreshWeather(weather);
                weathersToSave.add(weather);
            } catch (Exception e) {
                log.error("Error on fetch weather:[{}]:{}", address, ExceptionUtils.getMessage(e), e);
            }
        }
        save(weathersToSave);
        for (SuperCtlWeather weather : weathersToSave) {
            dbWeathers.put(weather.getAddress(), weather);
        }
        return dbWeathers;
    }

    private boolean isExpired(SuperCtlWeather weather) {
        Date lastRefreshTime = weather.getLastRefreshTime();
        return (System.currentTimeMillis() - lastRefreshTime.getTime()) < EXPIRE_MS;
    }

    private void refreshWeather(SuperCtlWeather weather) {
        WeatherInfo weatherInfo;
        try {
            weatherInfo = weatherApi.fetchWeather(weather.getAddress());
        } catch (WeatherException e) {
            throw new InternalServerErrorException(ExceptionUtils.getMessage(e), e);
        }
        weather.setTitle(weatherInfo.getTitle());
        weather.setTemperature(weatherInfo.getTemperature());
        weather.setHumidity(weatherInfo.getHumidity());
        weather.setInRain(weatherInfo.isInRain());
        weather.setExtInfo(weatherInfo.getExtraInfo());
        weather.setLastRefreshTime(DateTimeUtils.now());
        weather.setNeedRefresh(false);
    }

    @Override
    public Map<String, SuperCtlWeather> findWeathers(Set<String> addressArr) {
        List<SuperCtlWeather> weathers = superCtlWeatherMapper.selectByAddress(addressArr);
        Map<String, SuperCtlWeather> res = Maps.newHashMap();
        for (SuperCtlWeather weather : weathers) {
            res.put(weather.getAddress(), weather);
        }
        return res;
    }
}
