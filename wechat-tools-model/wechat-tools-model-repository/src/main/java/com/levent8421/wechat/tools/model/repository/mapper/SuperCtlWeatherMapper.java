package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.SuperCtlWeather;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:54
 * ClassName: SuperCtlAppMapper
 * Description:
 * SuperCtlWeatherMapper
 *
 * @author levent8421
 */
@Repository
public interface SuperCtlWeatherMapper extends AbstractMapper<SuperCtlWeather> {
    /**
     * 批量标记更新
     *
     * @param ids         ids
     * @param needRefresh needRefresh
     * @return rows
     */
    int updateNeedRefresh(@Param("ids") List<Integer> ids,
                          @Param("needRefresh") boolean needRefresh);
}
