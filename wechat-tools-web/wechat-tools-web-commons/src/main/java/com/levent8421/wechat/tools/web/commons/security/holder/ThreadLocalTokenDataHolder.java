package com.levent8421.wechat.tools.web.commons.security.holder;

import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 17:30
 * Class Name: ThreadLocalTokenDataHolder
 * Author: Levent8421
 * Description:
 * Token Data Holder implement by ThreadLocal
 *
 * @author Levent8421
 */
@Component
@Scope("singleton")
public class ThreadLocalTokenDataHolder implements TokenDataHolder {
    private final ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    private Map<String, Object> obtainDataMap() {
        Map<String, Object> data = local.get();
        if (data == null) {
            data = new HashMap<>(16);
            local.set(data);
        }
        return data;
    }

    @Override
    public void put(String name, Object value) {
        final Map<String, Object> data = obtainDataMap();
        data.put(name, value);
    }

    @Override
    public void putAll(Map<String, ?> map) {
        final Map<String, Object> data = obtainDataMap();
        data.putAll(map);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String name, Class<T> type) {
        final Object data = get(name);
        if (type.isInstance(data)) {
            return (T) data;
        }
        return null;
    }

    @Override
    public <T> T get(String name, Class<T> type, T defaultValue) {
        final T data = get(name, type);
        if (data == null) {
            return defaultValue;
        }
        return data;
    }

    @Override
    public Object get(String name) {
        return obtainDataMap().get(name);
    }

    @Override
    public void clearData() {
        local.remove();
    }
}
