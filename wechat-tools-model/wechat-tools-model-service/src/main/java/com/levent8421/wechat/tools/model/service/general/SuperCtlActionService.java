package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;
import com.levent8421.wechat.tools.model.service.general.listener.SuperCtlActionCompleteListener;

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

    /**
     * 通知操作超时
     *
     * @param id        id
     * @param awaitTime 等待时间
     */
    void notifyActionTimeout(Integer id, long awaitTime);

    /**
     * 监听action完成
     *
     * @param listener listener
     */
    void setActionCompleteListener(SuperCtlActionCompleteListener listener);

    /**
     * Mark action down
     *
     * @param id     id
     * @param msg    msg
     * @param status status
     */
    void reportActionDone(Integer id, String msg, SuperCtlDeviceStatus status);
}
