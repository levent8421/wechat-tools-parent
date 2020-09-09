package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 23:54
 * Class Name: AdminService
 * Author: Levent8421
 * Description:
 * 管理员账户相关也足组件定义
 *
 * @author Levent8421
 */
public interface AdminService extends AbstractService<Admin> {
    /**
     * 管理员登录
     *
     * @param loginName 登录名
     * @param password  登录密码
     * @return 管理员账户信息
     */
    Admin login(String loginName, String password);

    /**
     * 通过登录名查询管理员账户
     *
     * @param loginName 登录名
     * @return 账户信息
     */
    Admin findByLoginName(String loginName);

    /**
     * 创建管理员
     *
     * @param admin admin data
     * @return admin
     */
    Admin create(Admin admin);
}
