package com.levent8421.wechat.tools.web.admin.validation.fetcher;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create By leven ont 2020/9/8 0:24
 * Class Name :[StandardWechatTokenFetcherParamsValidator]
 * <p>
 * 标准微信token策略配置参数校验器
 *
 * @author leven
 */
@Component
public class StandardWechatTokenFetcherParamsValidator implements WechatTokenFetcherParamValidator {
    private static final int STRATEGY_CODE = 0x01;

    @Override
    public int getStrategyCode() {
        return STRATEGY_CODE;
    }

    @Override
    public void validate(Map<String, Object> config) {
        // Do Nothing
        // 该模式下，appId 与 secret 可从merchant 中直接获取，不需要额外的配置参数
    }
}
