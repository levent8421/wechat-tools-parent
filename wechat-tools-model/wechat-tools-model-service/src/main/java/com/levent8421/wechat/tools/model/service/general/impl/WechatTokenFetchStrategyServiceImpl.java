package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.model.repository.mapper.WechatTokenFetchStrategyMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import org.springframework.stereotype.Service;

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
}
