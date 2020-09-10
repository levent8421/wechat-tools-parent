package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.validate.fetcher.WechatTokenFetcherParamValidators;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Create By Levent8421
 * Create Time: 2020/9/10 16:02
 * Class Name: ApiWechatTokenFetchStrategyController
 * Author: Levent8421
 * Description:
 * 微信token获取策略相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/token-strategy")
public class ApiWechatTokenFetchStrategyController extends AbstractController {
    private final WechatTokenFetchStrategyService wechatTokenFetchStrategyService;
    private final WechatTokenFetcherParamValidators wechatTokenFetcherParamValidators;

    public ApiWechatTokenFetchStrategyController(WechatTokenFetchStrategyService wechatTokenFetchStrategyService,
                                                 WechatTokenFetcherParamValidators wechatTokenFetcherParamValidators) {
        this.wechatTokenFetchStrategyService = wechatTokenFetchStrategyService;
        this.wechatTokenFetcherParamValidators = wechatTokenFetcherParamValidators;
    }

    /**
     * 获取指定商户的微信令牌获取策略
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/_by-merchant")
    public GeneralResult<WechatTokenFetchStrategy> findByMerchant(@RequestParam("merchantId") Integer merchantId) {
        final WechatTokenFetchStrategy strategy = wechatTokenFetchStrategyService.findByMerchant(merchantId);
        if (strategy == null) {
            throw new ResourceNotFoundException("策略未配置！");
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
