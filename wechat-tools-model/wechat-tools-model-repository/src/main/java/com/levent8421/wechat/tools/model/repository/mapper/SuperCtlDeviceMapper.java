package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:54
 * ClassName: SuperCtlAppMapper
 * Description:
 * SuperCtlDeviceMapper
 *
 * @author levent8421
 */
@Repository
public interface SuperCtlDeviceMapper extends AbstractMapper<SuperCtlDevice> {
    /**
     * Update device status
     *
     * @param id        device id
     * @param statusStr status str
     * @return rows
     */
    int updateStatus(@Param("id") Integer id,
                     @Param("statusStr") String statusStr);

    /**
     * update device state by sn
     *
     * @param sn        sn
     * @param statusStr statue str
     * @return rows
     */
    int updateStatusBySn(@Param("sn") String sn,
                         @Param("statusStr") String statusStr);
}
