package com.levent8421.wechat.tools.cache.memory;

import com.levent8421.wechat.tools.cache.Cacheable;
import com.levent8421.wechat.tools.cache.HashCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 1:09
 * Class Name: MemoryHashCache
 * Author: Levent8421
 * Description:
 * hash cache implement by memory
 *
 * @author Levent8421
 */
public class MemoryHashCache<T extends Cacheable> implements HashCache<T> {
    private final Map<String, T> cache;

    public MemoryHashCache() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void put(T obj) {
        final String key = obj.getKey();
        cache.put(key, obj);
    }

    @Override
    public void putAll(Collection<T> objs) {
        for (T obj : objs) {
            put(obj);
        }
    }

    @Override
    public T get(String key) {
        return cache.get(key);
    }

    @Override
    public List<T> getAll(Collection<String> keys) {
        final List<T> res = new ArrayList<>();
        for (String key : keys) {
            if (cache.containsKey(key)) {
                res.add(get(key));
            }
        }
        return res;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public void removeAll(Collection<String> keys) {
        for (String key : keys) {
            remove(key);
        }
    }
}
