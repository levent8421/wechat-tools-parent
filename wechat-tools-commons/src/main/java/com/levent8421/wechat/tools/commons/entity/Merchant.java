package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 0:11
 * Class Name: Merchant
 * Author: Levent8421
 * Description:
 * Merchant 商户实体类
 *
 * @author Levent8421
 */
@Table(name = Merchant.TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
@Data
public class Merchant extends AbstractEntity {
    /**
     * Binding database table name
     */
    public static final String TABLE_NAME = "t_merchant";
    /**
     * Merchant Serial Number
     */
    @Column(name = "sn", nullable = false)
    private String sn;
    /**
     * Merchant name
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Merchant Logo file path
     */
    @Column(name = "logo_path")
    private String logoPath;
    /**
     * Merchant authorization code
     */
    @Column(name = "authorization_code", nullable = false)
    private String authorizationCode;
    /**
     * Merchant login name
     */
    @Column(name = "login_name", nullable = false)
    private String loginName;
    /**
     * Merchant login password (encoded)
     */
    @Column(name = "password", nullable = false)
    private String password;
}
