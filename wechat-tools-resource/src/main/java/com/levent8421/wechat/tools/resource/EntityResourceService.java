package com.levent8421.wechat.tools.resource;

import com.levent8421.wechat.tools.commons.entity.AbstractEntity;

import java.util.Collection;

/**
 * Create By leven ont 2020/9/14 20:23
 * Class Name :[EntityResourceService]
 * <p>
 * 实体对象相关资源业务封装
 *
 * @author leven
 */
public interface EntityResourceService<T extends AbstractEntity> {
    /**
     * 处理对象中的资源路径，
     * 即将对象中的相对路径替换为可直接访问的URL路径
     * 主要用于完成CDN路径的匹配
     *
     * @param entity 数据对象
     */
    void resolveStaticPath(T entity);

    /**
     * 处理对象中的资源路径，
     *
     * @param entities 数据对象
     */
    void resolveStaticPath(Collection<T> entities);
}
