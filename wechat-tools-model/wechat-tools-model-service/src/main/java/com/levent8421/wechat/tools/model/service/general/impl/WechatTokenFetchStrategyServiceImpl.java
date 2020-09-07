package com.levent8421.wechat.tools.model.service.general.impl;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.model.repository.mapper.WechatTokenFetchStrategyMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Create By leven ont 2020/9/7 1:30
 * Class Name :[WechatTokenFetchStrategyServiceImpl]
 * <p>
 * 微信令牌获取策略相关业务行为实现
 *
 * @author leven
 */
@Service
public class WechatTokenFetchStrategyServiceImpl extends AbstractServiceImpl<WechatTokenFetchStrategy> implements WechatTokenFetchStrategyService {
    private final WechatTokenFetchStrategyMapper wechatTokenFetchStrategyMapper;

    public WechatTokenFetchStrategyServiceImpl(WechatTokenFetchStrategyMapper wechatTokenFetchStrategyMapper) {
        super(wechatTokenFetchStrategyMapper);
        this.wechatTokenFetchStrategyMapper = wechatTokenFetchStrategyMapper;
    }

    @Override
    public WechatTokenFetchStrategy findByMerchant(Integer merchantId) {
        final WechatTokenFetchStrategy query = new WechatTokenFetchStrategy();
        query.setMerchantId(merchantId);
        return findOneByQuery(query);
    }

    @Override
    public WechatTokenFetchStrategy applyFetcherConfig(Integer merchantId, Integer strategyCode, Map<String, Object> options) {
        WechatTokenFetchStrategy strategy = findByMerchant(merchantId);
        final String configJson = JSON.toJSONString(options);
        if (strategy == null) {
            strategy = createStrategy(merchantId, strategyCode, configJson);
        } else {
            strategy.setStrategyCode(strategyCode);
            strategy.setConfigJson(configJson);
            strategy = updateById(strategy);
        }
        return strategy;
    }

    private WechatTokenFetchStrategy createStrategy(Integer merchantId, Integer strategyCode, String configJson) {
        final WechatTokenFetchStrategy strategy = new WechatTokenFetchStrategy();
        strategy.setMerchantId(merchantId);
        strategy.setStrategyCode(strategyCode);
        strategy.setConfigJson(configJson);
        return save(strategy);
    }
}
