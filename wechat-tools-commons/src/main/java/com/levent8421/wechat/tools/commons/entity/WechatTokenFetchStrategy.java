package com.levent8421.wechat.tools.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * Create By Levent8421
 * Create Time: 2020/8/22 18:46
 * Class Name: WechatTokenFetchStrategy
 * Author: Levent8421
 * Description:
 * 微信token 获取策略
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_wechat_token_fetch_strategy")
public class WechatTokenFetchStrategy extends AbstractEntity {
    /**
     * 商户ID
     */
    private Integer merchantId;
    /**
     * 策略码
     */
    private Integer strategyCode;
    /**
     * 策略配置
     */
    private String configJson;
}
