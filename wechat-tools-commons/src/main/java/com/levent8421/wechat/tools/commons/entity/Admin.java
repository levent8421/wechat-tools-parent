package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 21:24
 * Class Name: Admin
 * Author: Levent8421
 * Description:
 * Admin Entity
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_admin")
public class Admin extends AbstractEntity {
    /**
     * 登录名
     */
    @Column(name = "login_name", length = 100, nullable = false)
    private String loginName;
    /**
     * 管理员账户名
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 登录密码
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * 权限字符串
     */
    @Column(name = "authorization_str", nullable = false)
    private String authorizationStr;
}
