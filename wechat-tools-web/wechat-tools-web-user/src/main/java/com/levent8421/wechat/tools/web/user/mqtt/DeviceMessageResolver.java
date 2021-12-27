package com.levent8421.wechat.tools.web.user.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.DeviceMessageListener;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.config.SuperCtlConf;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

/**
 * Create By leven ont 2021/12/24 22:02
 * Class Name :[DeviceMessageResolver]
 * <p>
 * 设备消息处理
 *
 * @author leven
 */
@Slf4j
@Component
public class DeviceMessageResolver implements DeviceMessageListener {
    private static final String TYPE_ACK = "ack";
    private final SuperCtlConf superCtlConf;
    private final DeviceMessageClient deviceMessageClient;
    private final SuperCtlActionService superCtlActionService;
    private final SuperCtlDeviceService superCtlDeviceService;

    public DeviceMessageResolver(SuperCtlConf superCtlConf,
                                 DeviceMessageClient deviceMessageClient,
                                 SuperCtlActionService superCtlActionService,
                                 SuperCtlDeviceService superCtlDeviceService) {
        this.superCtlConf = superCtlConf;
        this.deviceMessageClient = deviceMessageClient;
        this.superCtlActionService = superCtlActionService;
        this.superCtlDeviceService = superCtlDeviceService;
    }

    @PostMapping
    public void subscribe() {
        try {
            this.deviceMessageClient.subscribe(superCtlConf.getSuperCtlUpstreamTopic(), this);
        } catch (MessageException e) {
            throw new RuntimeException(ExceptionUtils.getMessage(e), e);
        }
    }

    @Override
    public void onMessage(String topic, byte[] payload) {
        JSONObject message = JSON.parseObject(payload, JSONObject.class);
        String type = message.getString("type");
        if (type == null) {
            log.warn("Resolve Empty Msg Type from device:{}", new String(payload));
            return;
        }
        if (Objects.equals(TYPE_ACK, type)) {
            this.resolveActionAck(message);
        } else {
            log.warn("Resolve Invalidate Msg type from device:{}", new String(payload));
        }
    }

    private void resolveActionAck(JSONObject message) {
        String msg = message.getString("msg");
        Integer actionId = message.getInteger("seq");
        SuperCtlDeviceStatus status = message.getObject("state", SuperCtlDeviceStatus.class);
        String sn = message.getString("sn");

        if (actionId == null || actionId <= 0) {
            // State report
            if (StringUtils.isBlank(sn)) {
                log.warn("Resolve bad device message(Empty sn):{}", message);
                return;
            }
            superCtlDeviceService.updateStatus(sn, status);
            log.debug("Update device[{}] status:{}", sn, status);
        } else {
            // Action ack
            SuperCtlAction action = superCtlActionService.require(actionId);
            superCtlActionService.reportActionDone(action, msg, status);
            superCtlDeviceService.updateStatus(action.getDeviceId(), status);
            log.info("Action done:[{}]", actionId);
        }
    }
}
