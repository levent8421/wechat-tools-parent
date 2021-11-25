package com.levent8421.wechat.tools.model.service.general.impl;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlActionMapper;
import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlActionStatus;
import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlActionTypes;
import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.app.sc.SuperCtlMessage;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.config.SuperCtlConf;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

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
    private final SuperCtlActionMapper superCtlAppMapper;
    private final SuperCtlConf superCtlConf;

    public SuperCtlActionServiceImpl(DeviceMessageClient messageClient,
                                     SuperCtlActionMapper mapper,
                                     SuperCtlConf superCtlConf) {
        super(mapper);
        this.messageClient = messageClient;
        this.superCtlAppMapper = mapper;
        this.superCtlConf = superCtlConf;
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
            messageClient.publish(topic, String.valueOf(savedAction.getId()), payload);
        } catch (MessageException e) {
            savedAction.setStateCode(SuperCtlActionStatus.STATE_FAIL);
            savedAction.setResponseMsg("Send fail:" + ExceptionUtils.getMessage(e));
            updateById(savedAction);
            log.error("Error on send state ctl msg", e);
        }
        return savedAction;
    }
}
