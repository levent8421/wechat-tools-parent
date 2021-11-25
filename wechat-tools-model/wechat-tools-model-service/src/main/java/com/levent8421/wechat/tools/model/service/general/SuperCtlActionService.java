package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:05
 * ClassName: SuperCtlAppService
 * Description:
 * SuperCtl Action Service interface
 *
 * @author levent8421
 */
public interface SuperCtlActionService extends AbstractService<SuperCtlAction> {
    /**
     * 发送控制指令
     *
     * @param device       device
     * @param targetStatus status
     * @return action
     */
    SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlDeviceStatus targetStatus);
}
