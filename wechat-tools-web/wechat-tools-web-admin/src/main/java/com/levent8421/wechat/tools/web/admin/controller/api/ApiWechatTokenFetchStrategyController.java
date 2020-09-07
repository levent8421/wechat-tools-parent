package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import com.levent8421.wechat.tools.web.admin.validation.fetcher.WechatTokenFetcherParamValidators;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Create By leven ont 2020/9/7 1:33
 * Class Name :[ApiWechatTokenFetchStrategyController]
 * <p>
 * 微信令牌获取策略相关API数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/token-fetch")
@Slf4j
public class ApiWechatTokenFetchStrategyController extends AbstractController {
    private final WechatTokenFetchStrategyService wechatTokenFetchStrategyService;
    private final WechatTokenFetcherParamValidators wechatTokenFetcherParamValidators;

    public ApiWechatTokenFetchStrategyController(WechatTokenFetchStrategyService wechatTokenFetchStrategyService,
                                                 WechatTokenFetcherParamValidators wechatTokenFetcherParamValidators) {
        this.wechatTokenFetchStrategyService = wechatTokenFetchStrategyService;
        this.wechatTokenFetcherParamValidators = wechatTokenFetcherParamValidators;
    }

    /**
     * 通过商户查找微信令牌获取策略
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<WechatTokenFetchStrategy> findByMerchant(@RequestParam("merchantId") Integer merchantId) {
        final WechatTokenFetchStrategy strategy = wechatTokenFetchStrategyService.findByMerchant(merchantId);
        if (strategy == null) {
            throw new ResourceNotFoundException("Can not find tokenFetcher for merchant " + merchantId);
        }
        return GeneralResult.ok(strategy);
    }

    /**
     * 设置微信令牌获取策略
     *
     * @param merchantId 商户ID
     * @param options    配置参数
     * @return GR
     */
    @PostMapping("/merchant/{merchantId}")
    public GeneralResult<WechatTokenFetchStrategy> setupTokenFetcher(@PathVariable("merchantId") Integer merchantId,
                                                                     @RequestBody Map<String, Object> options) {
        final Integer strategyCode = wechatTokenFetcherParamValidators.validateAndGetStrategyCode(options);
        final WechatTokenFetchStrategy strategy = wechatTokenFetchStrategyService.applyFetcherConfig(merchantId, strategyCode, options);
        return GeneralResult.ok(strategy);
    }
}
