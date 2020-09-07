package com.levent8421.wechat.tools.web.admin.validation.fetcher;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.contains;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;

/**
 * Create By leven ont 2020/9/8 0:33
 * Class Name :[WechatTokenFetcherParamValidators]
 * <p>
 * 微信token fetcher配置参数校验器工具
 *
 * @author leven
 */
@Component
public class WechatTokenFetcherParamValidators {
    private static final String STRATEGY_CODE_KEY = "strategyCode";
    private final List<WechatTokenFetcherParamValidator> validators;
    private final Map<Integer, WechatTokenFetcherParamValidator> validatorMap;

    public WechatTokenFetcherParamValidators(List<WechatTokenFetcherParamValidator> validators) {
        this.validators = validators;
        this.validatorMap = buildValidatorMap();
    }

    private Map<Integer, WechatTokenFetcherParamValidator> buildValidatorMap() {
        final Map<Integer, WechatTokenFetcherParamValidator> tmpValidatorMap = new HashMap<>(16);
        for (WechatTokenFetcherParamValidator validator : validators) {
            final int strategyCode = validator.getStrategyCode();
            if (tmpValidatorMap.containsKey(strategyCode)) {
                throw new IllegalStateException("Duplicate FetcherValidator for strategy [" + strategyCode + "]");
            }
            tmpValidatorMap.put(strategyCode, validator);
        }
        return tmpValidatorMap;
    }

    private void validate(Integer strategyCode, Map<String, Object> options) {
        if (!validatorMap.containsKey(strategyCode)) {
            throw new BadRequestException("不存在的策略码" + strategyCode);
        }
        final WechatTokenFetcherParamValidator validator = validatorMap.get(strategyCode);
        validator.validate(options);
    }

    public Integer validateAndGetStrategyCode(Map<String, Object> options) {
        final Class<BadRequestException> error = BadRequestException.class;
        notEmpty(options, error, "参数为空");
        contains(options, STRATEGY_CODE_KEY, error, "策略码必填！");
        final Object strategyCodeObj = options.get(STRATEGY_CODE_KEY);
        if (!(strategyCodeObj instanceof Integer)) {
            throw new BadRequestException("非法策略码");
        }
        final Integer strategyCode = (Integer) strategyCodeObj;
        validate(strategyCode, options);
        return strategyCode;
    }
}
