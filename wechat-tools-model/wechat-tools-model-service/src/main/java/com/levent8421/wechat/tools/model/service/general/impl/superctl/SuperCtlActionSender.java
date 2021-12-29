package com.levent8421.wechat.tools.model.service.general.impl.superctl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionStatus;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionTypes;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.app.sc.msg.SuperCtlMessageManager;
import com.levent8421.wechat.tools.model.service.app.sc.vo.SuperCtlMessage;
import com.levent8421.wechat.tools.model.service.app.sc.vo.SuperCtlTaskCtlParam;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create By leven ont 2021/12/29 22:37
 * Class Name :[SuperCtlActionSender]
 * <p>
 * Send message
 *
 * @author leven
 */
@Slf4j
public class SuperCtlActionSender {
    /**
     * 动作发送行为定义
     */
    public interface ActionSender {
        /**
         * 发送设备控制动作
         *
         * @param messageClient Message Client
         * @param action        动作对象
         * @param topic         topic
         * @throws Exception error
         */
        void sendAction(DeviceMessageClient messageClient, String topic, SuperCtlAction action) throws Exception;
    }

    private final Map<String, ActionSender> senderTable = Maps.newHashMap();
    private final SuperCtlMessageManager messageManager;
    private final SuperCtlActionService actionService;

    public SuperCtlActionSender(SuperCtlMessageManager messageManager, SuperCtlActionService actionService) {
        this.messageManager = messageManager;
        this.actionService = actionService;
        this.loadSenderTable();
    }

    private void loadSenderTable() {
        senderTable.put(SuperCtlActionTypes.STATE_CTL, (client, topic, action) -> {
            SuperCtlDeviceStatus status = JSON.parseObject(action.getStateJsonExpect(), SuperCtlDeviceStatus.class);
            sendMessage(client, topic, action.getId(), SuperCtlActionTypes.STATE_CTL, status);
        });
        senderTable.put(SuperCtlActionTypes.TASK_CTL, (client, topic, action) -> {
            SuperCtlDeviceStatus status = JSON.parseObject(action.getStateJsonExpect(), SuperCtlDeviceStatus.class);
            SuperCtlTaskCtlParam param = new SuperCtlTaskCtlParam();
            param.setOrderTime(action.getOrderTime());
            param.setStatus(status);
            sendMessage(client, topic, action.getId(), SuperCtlActionTypes.TASK_CTL, param);
        });
    }

    private void sendMessage(DeviceMessageClient client, String topic, Integer seqId, String actionName, Object payload) throws MessageException {
        SuperCtlMessage<Object> message = new SuperCtlMessage<>();
        message.setId(seqId);
        message.setAction(actionName);
        message.setPayload(payload);
        byte[] msgBytes = JSON.toJSONBytes(message);
        messageManager.register(message);
        client.publish(topic, String.valueOf(seqId), msgBytes);
    }

    /**
     * Dispatch action to target sender
     *
     * @param messageClient client
     * @param topic         Topic
     * @param action        action
     */
    public void sendAction(DeviceMessageClient messageClient, String topic, SuperCtlAction action) {
        String actionType = action.getTypeCode();
        ActionSender sender = senderTable.get(actionType);
        if (sender == null) {
            throw new IllegalArgumentException("Invalidate ActionType:" + actionType);
        }
        try {
            sender.sendAction(messageClient, topic, action);
        } catch (Exception e) {
            log.error("Error on send action[{}]", action.getId(), e);
            action.setStateCode(SuperCtlActionStatus.STATE_FAIL);
            action.setResponseMsg(ExceptionUtils.getMessage(e));
            actionService.updateById(action);
            actionService.notifyActionComplete(action);
        }
    }
}
