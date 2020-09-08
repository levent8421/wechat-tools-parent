package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatTokenFetchStrategy;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.model.service.general.WechatTokenFetchStrategyService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.wechat.api.WechatApi;
import com.levent8421.wechat.tools.wechat.api.vo.WechatJsApiConfigParam;
import com.levent8421.wechat.tools.wechat.token.fetcher.WechatTokenFetchers;
import com.levent8421.wechat.tools.wechat.token.vo.WechatAppToken;
import com.levent8421.wechat.tools.wechat.token.vo.WechatJsApiTicket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2020/9/8 21:15
 * Class Name :[ApiWechatTokenController]
 * <p>
 * 微信令牌相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/wechat-token")
public class ApiWechatTokenController extends AbstractController {
    private final WechatTokenFetchers wechatTokenFetchers;
    private final WechatTokenFetchStrategyService wechatTokenFetchStrategyService;
    private final MerchantService merchantService;
    private final WechatApi wechatApi;

    public ApiWechatTokenController(WechatTokenFetchers wechatTokenFetchers,
                                    WechatTokenFetchStrategyService wechatTokenFetchStrategyService,
                                    MerchantService merchantService,
                                    WechatApi wechatApi) {
        this.wechatTokenFetchers = wechatTokenFetchers;
        this.wechatTokenFetchStrategyService = wechatTokenFetchStrategyService;
        this.merchantService = merchantService;
        this.wechatApi = wechatApi;
    }

    private WechatTokenFetchStrategy findStrategy(Integer merchantId) {
        final WechatTokenFetchStrategy strategy = wechatTokenFetchStrategyService.findByMerchant(merchantId);
        if (strategy == null) {
            final String error = String.format("Can not find TokenStrategy for merchant [%s]", merchantId);
            throw new ResourceNotFoundException(error);
        }
        return strategy;
    }

    /**
     * 获取微信APP token
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/_app-token")
    public GeneralResult<WechatAppToken> fetchAppToken(@RequestParam("merchantId") Integer merchantId) {
        final Merchant merchant = merchantService.require(merchantId);
        final WechatTokenFetchStrategy strategy = findStrategy(merchantId);
        final WechatAppToken token = wechatTokenFetchers.fetchAppToken(merchant, strategy);
        return GeneralResult.ok(token);
    }

    /**
     * 获取JS API Ticket
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/_js-api-ticket")
    public GeneralResult<WechatJsApiTicket> fetchJsApiTicket(@RequestParam("merchantId") Integer merchantId) {
        final Merchant merchant = merchantService.require(merchantId);
        final WechatTokenFetchStrategy strategy = findStrategy(merchantId);
        final WechatJsApiTicket jsApiTicket = wechatTokenFetchers.fetchJsApiTicket(merchant, strategy);
        return GeneralResult.ok(jsApiTicket);
    }

    /**
     * 获取微信JS API 配置参数
     *
     * @param merchantId 商户ID
     * @param url        url
     * @return GR
     */
    @GetMapping("/_js-api-config")
    public GeneralResult<WechatJsApiConfigParam> wechatJsApiConfigParam(@RequestParam("merchantId") Integer merchantId,
                                                                        @RequestParam("url") String url) {
        final Merchant merchant = merchantService.require(merchantId);
        final WechatTokenFetchStrategy strategy = findStrategy(merchantId);
        final WechatJsApiTicket ticket = wechatTokenFetchers.fetchJsApiTicket(merchant, strategy);
        final WechatJsApiConfigParam param = wechatApi.getJsApiConfigParam(ticket.getTicket(), merchant.getWechatAppId(), url);
        return GeneralResult.ok(param);
    }
}
