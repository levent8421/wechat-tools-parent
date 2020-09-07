package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.Map;

/**
 * Create By leven ont 2020/9/7 1:29
 * Class Name :[WechatTokenFetchStrategyService]
 * <p>
 * 微信token获取策略相关业务行为定义
 *
 * @author leven
 */
public interface WechatTokenFetchStrategyService extends AbstractService<WechatTokenFetchStrategy> {
    /**
     * find strategy by merchant id
     *
     * @param merchantId 商户ID
     * @return strategy
     */
    WechatTokenFetchStrategy findByMerchant(Integer merchantId);

    /**
     * 配置微信令牌获取策略
     *
     * @param merchantId   商户ID
     * @param strategyCode 策略码
     * @param options      策略配置
     * @return GR
     */
    WechatTokenFetchStrategy applyFetcherConfig(Integer merchantId, Integer strategyCode, Map<String, Object> options);
}
