package com.levent8421.wechat.tools.model.service.general.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.utils.datetime.DateTimeUtils;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlActionMapper;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionStatus;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlActionTypes;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.app.sc.msg.SuperCtlMessageManager;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.config.SuperCtlConf;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.impl.superctl.SuperCtlActionSender;
import com.levent8421.wechat.tools.model.service.general.listener.SuperCtlActionCompleteListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Objects;

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
    private final SuperCtlActionMapper superCtlActionMapper;
    private final SuperCtlConf superCtlConf;
    private final SuperCtlMessageManager superCtlMessageManager;
    private SuperCtlActionCompleteListener actionCompleteListener;
    private final SuperCtlActionSender actionSender;

    public SuperCtlActionServiceImpl(SuperCtlActionMapper mapper,
                                     SuperCtlConf superCtlConf) {
        super(mapper);
        this.superCtlActionMapper = mapper;
        this.superCtlConf = superCtlConf;
        superCtlMessageManager = new SuperCtlMessageManager(this);
        actionSender = new SuperCtlActionSender(superCtlMessageManager, this);
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
    public SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlDeviceStatus targetStatus, DeviceMessageClient messageClient) {
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
        actionSender.sendAction(messageClient, topic, action);
        return savedAction;
    }

    @Override
    public SuperCtlAction sendAction(SuperCtlDevice device, SuperCtlAction action, DeviceMessageClient client) {
        int tasks = superCtlActionMapper.selectCountByTypeAndState(device.getId(), action.getTypeCode(), SuperCtlActionStatus.STATE_START);
        if (tasks >= SuperCtlAction.MAX_TASK_ACTIONS) {
            throw new BadRequestException("Max Tasks=" + SuperCtlAction.MAX_TASK_ACTIONS);
        }
        action = save(action);
        String topic = superCtlConf.getSuperCtlDownstreamTopicPrefix() + device.getSn();
        actionSender.sendAction(client, topic, action);
        return action;
    }

    @Override
    public void notifyActionComplete(SuperCtlAction action) {
        if (actionCompleteListener != null) {
            actionCompleteListener.onComplete(action);
        }
        superCtlMessageManager.remove(action.getId());
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
    public void reportActionDone(SuperCtlAction action, String msg, SuperCtlDeviceStatus status) {
        String stateJsonExpect = action.getStateJsonExpect();
        SuperCtlDeviceStatus expect = JSON.parseObject(stateJsonExpect, SuperCtlDeviceStatus.class);
        if (!expect.equals(status) && Objects.equals(action.getTypeCode(), SuperCtlActionTypes.STATE_CTL)) {
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
        superCtlMessageManager.remove(action.getId());
    }

    @Override
    public PageInfo<SuperCtlAction> findByType(String type, Integer page, Integer rows) {
        return PageHelper.startPage(page, rows)
                .doSelectPageInfo(() -> superCtlActionMapper.selectByType(type));
    }

    @Override
    public PageInfo<SuperCtlAction> findByTypeAndState(String type, String state, Integer page, Integer rows) {
        return PageHelper.startPage(page, rows)
                .doSelectPageInfo(() -> superCtlActionMapper.selectByTypeAndState(type, state));
    }
}
