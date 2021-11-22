package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by Levent8421
 * Date: 2021/11/22 20:25
 * ClassName: SuperCtlApp
 * Description:
 * SuperCtl APP Entity
 *
 * @author levent8421
 */
@Table(name = "t_super_ctl_app")
@EqualsAndHashCode(callSuper = true)
@Data
public class SuperCtlApp extends AbstractEntity {
    /**
     * 商户ID
     */
    @Column(name = "merchant_id", length = 10, nullable = false)
    private Integer merchantId;
    /**
     * 商户
     */
    private Merchant merchant;
    /**
     * Application name
     */
    @Column(name = "app_name", nullable = false)
    private String appName;
    /**
     * Description text
     */
    @Column(name = "description")
    private String description;
    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;
}
