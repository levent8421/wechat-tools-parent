package com.levent8421.wechat.tools.model.service.app.sc.define;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/25 17:37
 * ClassName: MotorStates
 * Description:
 * Motor State
 *
 * @author levent8421
 */
public class MotorStates {
    public static final String STATE_OFF = "off";
    public static final String STATE_FORWARD = "forward";
    public static final String STATE_BACKWARD = "backward";
    private static final Map<String, String> STATUS_MAP = Maps.newHashMap();

    static {
        STATUS_MAP.put(STATE_OFF, STATE_OFF);
        STATUS_MAP.put(STATE_FORWARD, STATE_FORWARD);
        STATUS_MAP.put(STATE_BACKWARD, STATE_BACKWARD);
    }

    public static boolean isStateInRule(String state) {
        return STATUS_MAP.containsKey(state);
    }
}
