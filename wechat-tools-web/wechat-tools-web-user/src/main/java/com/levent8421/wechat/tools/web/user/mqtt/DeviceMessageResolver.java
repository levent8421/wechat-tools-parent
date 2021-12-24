package com.levent8421.wechat.tools.web.user.mqtt;

import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.DeviceMessageListener;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.model.service.config.SuperCtlConf;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Create By leven ont 2021/12/24 22:02
 * Class Name :[DeviceMessageResolver]
 * <p>
 * 设备消息处理
 *
 * @author leven
 */
@Component
public class DeviceMessageResolver implements DeviceMessageListener {
    private final SuperCtlConf superCtlConf;
    private final DeviceMessageClient deviceMessageClient;

    public DeviceMessageResolver(SuperCtlConf superCtlConf, DeviceMessageClient deviceMessageClient) {
        this.superCtlConf = superCtlConf;
        this.deviceMessageClient = deviceMessageClient;
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

    }
}
