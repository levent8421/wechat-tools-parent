package com.levent8421.wechat.tools.web.commons.security;

import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 17:08
 * Class Name: TokenDataHolder
 * Author: Levent8421
 * Description:
 * 令牌携带参数保持器
 *
 * @author Levent8421
 */
public interface TokenDataHolder {
    /**
     * 添加需要保持的数据
     *
     * @param name  data name
     * @param value data value
     */
    void put(String name, Object value);

    /**
     * Put all map data into houlder
     *
     * @param map map
     */
    void putAll(Map<String, ?> map);

    /**
     * 获取指定类型的数据
     * 若数据不存在或者不是该类型 则返回null
     *
     * @param name name
     * @param type data type
     * @param <T>  type
     * @return data
     */
    <T> T get(String name, Class<T> type);

    /**
     * Get data or default value
     *
     * @param name         name
     * @param type         type
     * @param defaultValue default value
     * @param <T>          type
     * @return data
     */
    <T> T get(String name, Class<T> type, T defaultValue);

    /**
     * Get object data
     *
     * @param name name
     * @return data
     */
    Object get(String name);

    /**
     * 清空数据
     */
    void clearData();
}
