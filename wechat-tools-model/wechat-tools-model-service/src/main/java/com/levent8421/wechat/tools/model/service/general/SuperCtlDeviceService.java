package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
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
}
