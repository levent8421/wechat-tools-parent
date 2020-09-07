package com.levent8421.wechat.tools.web.admin.validation.fetcher;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.contains;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;

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
    private static final String APP_ID_KEY = "appId";
    private static final String SECRET_KEY = "secret";

    @Override
    public int getStrategyCode() {
        return STRATEGY_CODE;
    }

    @Override
    public void validate(Map<String, Object> config) {
        final Class<BadRequestException> error = BadRequestException.class;
        notEmpty(config, error, "参数为空");
        contains(config, APP_ID_KEY, error, "请输入APP_ID");
        contains(config, SECRET_KEY, error, "请输入SECRET");
    }
}
