package com.levent8421.wechat.tools.message;

/**
 * Create by Levent8421
 * Date: 2021/11/25 13:43
 * ClassName: DeviceMessageListener
 * Description:
 * 设备消息监听
 *
 * @author levent8421
 */
public interface DeviceMessageListener {
    /**
     * callback
     *
     * @param topic   topic
     * @param payload payload
     */
    void onMessage(String topic, byte[] payload);
}
