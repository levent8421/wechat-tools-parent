package com.levent8421.wechat.tools.web.commons.validate.fetcher;

import java.util.Map;

/**
 * Create By leven ont 2020/9/8 0:21
 * Class Name :[WechatTokenFetcherParamValidator]
 * <p>
 * 微信令牌配置参数校验器
 *
 * @author leven
 */
public interface WechatTokenFetcherParamValidator {
    /**
     * 获取策略码
     *
     * @return 策略码
     */
    int getStrategyCode();

    /**
     * 校验参数
     *
     * @param config 参数map
     */
    void validate(Map<String, Object> config);
}
