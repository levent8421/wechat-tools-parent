package com.levent8421.wechat.tools.cache;

import java.io.Serializable;

/**
 * Create By Levent8421
 * Create Time: 2020/8/24 12:09
 * Class Name: Cacheable
 * Author: Levent8421
 * Description:
 * 可缓存的对象
 *
 * @author Levent8421
 */
public interface Cacheable extends Serializable {
    /**
     * Get cache key
     *
     * @return key string
     */
    String getKey();
}
