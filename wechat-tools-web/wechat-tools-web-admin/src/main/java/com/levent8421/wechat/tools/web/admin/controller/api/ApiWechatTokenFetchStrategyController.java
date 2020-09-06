package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class ApiWechatTokenFetchStrategyController extends AbstractController {
    private final WechatTokenFetchStrategyService wechatTokenFetchStrategyService;

    public ApiWechatTokenFetchStrategyController(WechatTokenFetchStrategyService wechatTokenFetchStrategyService) {
        this.wechatTokenFetchStrategyService = wechatTokenFetchStrategyService;
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
}
