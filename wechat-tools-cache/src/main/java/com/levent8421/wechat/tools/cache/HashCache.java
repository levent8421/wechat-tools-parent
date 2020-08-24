package com.levent8421.wechat.tools.cache;

import java.util.Collection;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 1:02
 * Class Name: HashCache
 * Author: Levent8421
 * Description:
 * Hash Cache
 *
 * @author Levent8421
 */
public interface HashCache<T extends Cacheable> {
    /**
     * Cache a object
     *
     * @param obj object
     */
    void put(T obj);

    /**
     * cache objects
     *
     * @param objs objs
     */
    void putAll(Collection<T> objs);

    /**
     * Get a object
     *
     * @param key key
     * @return cacheable object
     */
    T get(String key);

    /**
     * Get objs
     *
     * @param keys key set
     * @return objs
     */
    List<T> getAll(Collection<String> keys);

    /**
     * remove a object from cache
     *
     * @param key key
     */
    void remove(String key);

    /**
     * remove objs from cache
     *
     * @param keys key set
     */
    void removeAll(Collection<String> keys);
}
