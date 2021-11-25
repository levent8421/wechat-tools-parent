package com.levent8421.wechat.tools.message.mqtt;

import com.google.common.collect.Maps;
import com.levent8421.wechat.tools.message.DeviceMessageClient;
import com.levent8421.wechat.tools.message.DeviceMessageListener;
import com.levent8421.wechat.tools.message.MessageException;
import com.levent8421.wechat.tools.message.mqtt.conf.MessageConf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/25 13:48
 * ClassName: MqttDeviceMessageClient
 * Description:
 * Mqtt impl
 *
 * @author levent8421
 */
@Slf4j
@Component
public class MqttDeviceMessageClient implements DeviceMessageClient, ApplicationListener<ApplicationReadyEvent>, MqttCallback {
    private static final int CONTROL_MSG_QOS = 1;
    private final MessageConf messageConf;
    private MqttClient mqttClient;
    private final Map<String, DeviceMessageListener> listenerTable = Maps.newHashMap();

    public MqttDeviceMessageClient(MessageConf messageConf) {
        this.messageConf = messageConf;
    }

    @Override
    public void publish(String topic, String traceId, byte[] payload) throws MessageException {
        MqttMessage message = new MqttMessage();
        message.setQos(CONTROL_MSG_QOS);
        message.setPayload(payload);
        message.setRetained(true);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            throw new MessageException(ExceptionUtils.getMessage(e), e);
        }
    }

    @Override
    public void subscribe(String topic, DeviceMessageListener listener) throws MessageException {
        try {
            mqttClient.subscribe(topic);
            listenerTable.put(topic, listener);
        } catch (MqttException e) {
            throw new MessageException(ExceptionUtils.getMessage(e), e);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // AUTO Connect
        autoConnect();
    }

    private void autoConnect() {
        String broker = messageConf.getBroker();
        String clientId = messageConf.getClientId();
        String username = messageConf.getUsername();
        String password = messageConf.getPassword();
        try {
            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
        } catch (MqttException e) {
            throw new RuntimeException(ExceptionUtils.getMessage(e), e);
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(messageConf.isAutoReconnect());
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setKeepAliveInterval(messageConf.getKeepAlive());
        options.setConnectionTimeout(messageConf.getConnectTimeout());
        try {
            mqttClient.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(ExceptionUtils.getMessage(e), e);
        }
        mqttClient.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("MQTT Connection lost", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        DeviceMessageListener listener = listenerTable.get(topic);
        if (listener == null) {
            log.warn("Invalidate topic: [{}]", topic);
            return;
        }
        listener.onMessage(topic, message.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.debug("MQTT deliveryComplete: [{}]", token.getMessageId());
    }
}
