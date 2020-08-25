package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 23:50
 * Class Name: AdminMapper
 * Author: Levent8421
 * Description:
 * 管理员账户相关数据库访问组件
 *
 * @author Levent8421
 */
@Repository
public interface AdminMapper extends AbstractMapper<Admin> {
    /**
     * 通过登录名查询管理员账户
     *
     * @param loginName 登录名
     * @return admin
     */
    Admin selectByLoginName(@Param("loginName") String loginName);
}
