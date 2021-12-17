package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:05
 * ClassName: SuperCtlAppService
 * Description:
 * SuperCtl Device Service interface
 *
 * @author levent8421
 */
public interface SuperCtlDeviceService extends AbstractService<SuperCtlDevice> {
    /**
     * Find device by uid
     *
     * @param uid uid
     * @return devices
     */
    List<SuperCtlDevice> findByUser(Integer uid);

    /**
     * Update status
     *
     * @param id     id
     * @param status status
     */
    void updateStatus(Integer id, SuperCtlDeviceStatus status);

    /**
     * Update status
     *
     * @param sn     sn
     * @param status status
     */
    void updateStatus(String sn, SuperCtlDeviceStatus status);

    /**
     * Bind New Device
     *
     * @param device device
     * @return device
     */
    SuperCtlDevice bindNewDevice(SuperCtlDevice device);

    /**
     * Find by sn
     *
     * @param sn sn
     * @return device
     */
    SuperCtlDevice findBySn(String sn);
}
