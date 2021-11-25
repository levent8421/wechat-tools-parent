package com.levent8421.wechat.tools.message;

/**
 * Create by Levent8421
 * Date: 2021/11/25 13:15
 * ClassName: DeviceMessageClient
 * Description:
 * m设备消息处理
 *
 * @author levent8421
 */
public interface DeviceMessageClient {
    /**
     * publish message
     *
     * @param topic   主题
     * @param traceId message id
     * @param payload payload
     * @throws MessageException e
     */
    void publish(String topic, String traceId, byte[] payload) throws MessageException;

    /**
     * 订阅消息
     *
     * @param topic    topic
     * @param listener listener
     * @throws MessageException e
     */
    void subscribe(String topic, DeviceMessageListener listener) throws MessageException;
}
