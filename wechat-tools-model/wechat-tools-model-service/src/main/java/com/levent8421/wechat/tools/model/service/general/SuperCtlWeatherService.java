package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * Find need Refresh weather records
     *
     * @param max max result
     * @return weather record
     */
    List<SuperCtlWeather> findNeedRefreshRecord(int max);

    /**
     * 刷新天气信息
     *
     * @param weather 天气记录
     */
    void refresh(SuperCtlWeather weather);

    /**
     * 获取天气信息（不存在时创建并刷新，存在时检查是否过期）
     *
     * @param addressArr 地址表
     * @return 天气表
     */
    Map<String, SuperCtlWeather> getWeathers(Set<String> addressArr);

    /**
     * 查询天气信息（仅操作数据库）
     *
     * @param addressArr 地址表
     * @return 天气表
     */
    Map<String, SuperCtlWeather> findWeathers(Set<String> addressArr);
}
