package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

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
}
