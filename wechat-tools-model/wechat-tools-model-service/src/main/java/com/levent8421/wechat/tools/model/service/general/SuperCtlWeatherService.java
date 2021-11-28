package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:05
 * ClassName: SuperCtlAppService
 * Description:
 * SuperCtl Weather Service interface
 *
 * @author levent8421
 */
public interface SuperCtlWeatherService extends AbstractService<SuperCtlWeather> {
    /**
     * 标记是否需要更新
     *
     * @param ids         ids
     * @param needRefresh needRefresh
     * @return rows
     */
    int markRefresh(List<Integer> ids, boolean needRefresh);
}
