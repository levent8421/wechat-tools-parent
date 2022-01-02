package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:54
 * ClassName: SuperCtlAppMapper
 * Description:
 * SuperCtlActionMapper
 *
 * @author levent8421
 */
@Repository
public interface SuperCtlActionMapper extends AbstractMapper<SuperCtlAction> {
    /**
     * Update state
     *
     * @param id           id
     * @param state        state
     * @param msg          msg
     * @param completeTime complete time
     * @return rows
     */
    int updateState(@Param("id") Integer id,
                    @Param("state") String state,
                    @Param("msg") String msg,
                    @Param("completeTime") Date completeTime);

    /**
     * select by type
     *
     * @param type type code
     * @return actions
     */
    List<SuperCtlAction> selectByType(@Param("type") String type);

    /**
     * 根据设备ID、操作类型、状态码查询操作数量
     *
     * @param deviceId  deviceId
     * @param typeCode  typeCode
     * @param stateCode stateCode
     * @return count
     */
    int selectCountByTypeAndState(@Param("deviceId") Integer deviceId,
                                  @Param("typeCode") String typeCode,
                                  @Param("stateCode") String stateCode);

    /**
     * Select by type and state
     *
     * @param type  type
     * @param state state
     * @return actions
     */
    List<SuperCtlAction> selectByTypeAndState(@Param("type") String type,
                                              @Param("state") String state);
}
