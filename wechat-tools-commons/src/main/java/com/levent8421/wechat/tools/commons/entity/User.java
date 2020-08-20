package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:11
 * Class Name: User
 * Author: Levent8421
 * Description:
 * 用户实体类
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_user")
public class User extends AbstractEntity {
    @Column(name = "merchant_id", length = 10, nullable = false)
    private Integer merchantId;
}
