package com.levent8421.wechat.tools.model.service.general.impl;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.utils.datetime.DateTimeUtils;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlActionMapper;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionStatus;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionTypes;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.app.sc.msg.SuperCtlMessageManager;
import com.levent8421.wechat.tools.model.service.app.sc.vo.SuperCtlMessage;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.config.SuperCtlConf;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.listener.SuperCtlActionCompleteListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlDeviceServiceImpl Class
 *
 * @author levent8421
 */
@Slf4j
@Service
public class SuperCtlActionServiceImpl extends AbstractServiceImpl<SuperCtlAction> implements SuperCtlActionService {
    private final DeviceMessageClient messageClient;
    private final SuperCtlActionMapper superCtlActionMapper;
    private final SuperCtlConf superCtlConf;
    private final SuperCtlMessageManager superCtlMessageManager;
    private SuperCtlActionCompleteListener actionCompleteListener;

    public SuperCtlActionServiceImpl(DeviceMessageClient messageClient,
                                     SuperCtlActionMapper mapper,
                                     SuperCtlConf superCtlConf) {
        super(mapper);
        this.messageClient = messageClient;
        this.superCtlActionMapper = mapper;
        this.superCtlConf = superCtlConf;
        superCtlMessageManager = new SuperCtlMessageManager(this);
    }

    @PostConstruct
    public void messageManagerInit() {
        superCtlMessageManager.startCheckTask();
    }

    @PreDestroy
    public void messageManagerDeInit() {
        superCtlMessageManager.stopCheckTask();
    }

    @Override
    public SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlDeviceStatus targetStatus) {
        SuperCtlAction action = new SuperCtlAction();
        action.setDevice(device);
        action.setDeviceId(device.getId());
        action.setTypeCode(SuperCtlActionTypes.STATE_CTL);
        action.setStateJsonBefore(JSON.toJSONString(device.getStatusJson()));
        action.setStateJsonExpect(JSON.toJSONString(targetStatus));
        action.setStartTime(new Date());
        action.setStateCode(SuperCtlActionStatus.STATE_START);
        SuperCtlAction savedAction = save(action);

        String topic = superCtlConf.getSuperCtlDownstreamTopicPrefix() + device.getSn();
        SuperCtlMessage<SuperCtlDeviceStatus> message = new SuperCtlMessage<>();
        message.setId(savedAction.getId());
        message.setAction(SuperCtlActionTypes.STATE_CTL);
        message.setPayload(targetStatus);
        byte[] payload = JSON.toJSONBytes(message);
        try {
            superCtlMessageManager.register(message);
            messageClient.publish(topic, String.valueOf(savedAction.getId()), payload);
        } catch (MessageException e) {
            savedAction.setStateCode(SuperCtlActionStatus.STATE_FAIL);
            savedAction.setResponseMsg("Send fail:" + ExceptionUtils.getMessage(e));
            updateById(savedAction);
            notifyActionComplete(savedAction);
            log.error("Error on send state ctl msg", e);
        }
        return savedAction;
    }

    private void notifyActionComplete(SuperCtlAction action) {
        if (actionCompleteListener != null) {
            actionCompleteListener.onComplete(action);
        }
    }

    @Override
    public void notifyActionTimeout(Integer id, long awaitTime) {
        log.warn("Message[{}] timeout, await=[{}]", id, awaitTime);
        SuperCtlAction action = require(id);
        String resp = "timeout:" + awaitTime;
        Date now = DateTimeUtils.now();
        superCtlActionMapper.updateState(id, SuperCtlActionStatus.STATE_TIMEOUT, resp, now);
        action.setResponseMsg(resp);
        action.setCompleteTime(now);
        action.setStateCode(SuperCtlActionStatus.STATE_TIMEOUT);
        notifyActionComplete(action);
    }

    @Override
    public void setActionCompleteListener(SuperCtlActionCompleteListener listener) {
        this.actionCompleteListener = listener;
    }

    @Override
    public void reportActionDone(Integer id, String msg, SuperCtlDeviceStatus status) {
        SuperCtlAction action = require(id);
        String stateJsonExpect = action.getStateJsonExpect();
        SuperCtlDeviceStatus expect = JSON.parseObject(stateJsonExpect, SuperCtlDeviceStatus.class);
        if (!expect.equals(status)) {
            log.warn("Conflict state on expect[{}] and DeviceReport[{}]", expect, status);
            action.setStateCode(SuperCtlActionStatus.STATE_CONFLICT);
        } else {
            action.setStateCode(SuperCtlActionStatus.STATE_SUCCESS);
        }
        action.setCompleteTime(DateTimeUtils.now());
        action.setResponseMsg(msg);
        action.setStateJsonAfter(JSON.toJSONString(status));
        SuperCtlAction updatedAction = updateById(action);
        notifyActionComplete(updatedAction);
        superCtlMessageManager.remove(id);
    }
}
