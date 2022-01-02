package com.levent8421.wechat.tools.model.service.general;

import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
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
     * @param device        device
     * @param targetStatus  status
     * @param messageClient message client
     * @return action
     */
    SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlDeviceStatus targetStatus, DeviceMessageClient messageClient);

    /**
     * 发送操作指令
     *
     * @param device device
     * @param action action
     * @param client mqttClient
     * @return action
     */
    SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlAction action, DeviceMessageClient client);

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
     * @param action action
     * @param msg    msg
     * @param status status
     */
    void reportActionDone(SuperCtlAction action, String msg, SuperCtlDeviceStatus status);

    /**
     * 通知操作结束
     *
     * @param action action
     */
    void notifyActionComplete(SuperCtlAction action);

    /**
     * Find action by type with pagination
     *
     * @param type type
     * @param page page
     * @param rows rows
     * @return PageInfo
     */
    PageInfo<SuperCtlAction> findByType(String type, Integer page, Integer rows);

    /**
     * Find action by type and state with pagination
     *
     * @param type  type
     * @param state state
     * @param page  page
     * @param rows  rows
     * @return PageInfo
     */
    PageInfo<SuperCtlAction> findByTypeAndState(String type, String state, Integer page, Integer rows);
}
