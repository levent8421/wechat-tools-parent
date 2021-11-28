package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlWeatherMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlWeatherService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlDeviceServiceImpl Class
 *
 * @author levent8421
 */
@Service
public class SuperCtlWeatherServiceImpl extends AbstractServiceImpl<SuperCtlWeather> implements SuperCtlWeatherService {
    private final SuperCtlWeatherMapper superCtlWeatherMapper;

    public SuperCtlWeatherServiceImpl(SuperCtlWeatherMapper mapper) {
        super(mapper);
        this.superCtlWeatherMapper = mapper;
    }

    @Override
    public int markRefresh(List<Integer> ids, boolean needRefresh) {
        return superCtlWeatherMapper.updateNeedRefresh(ids, needRefresh);
    }
}
