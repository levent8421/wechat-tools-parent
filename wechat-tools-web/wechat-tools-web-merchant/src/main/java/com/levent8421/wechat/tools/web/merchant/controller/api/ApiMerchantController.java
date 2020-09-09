package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2020/9/9 23:19
 * Class Name :[ApiMerchantController]
 * <p>
 * 商户相关API数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/merchant")
public class ApiMerchantController extends AbstractController {
    private final TokenDataHolder tokenDataHolder;
    private final MerchantService merchantService;

    public ApiMerchantController(TokenDataHolder tokenDataHolder,
                                 MerchantService merchantService) {
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
    }

    /**
     * 获取当前登录的商户
     *
     * @return GR
     */
    @GetMapping("/_me")
    public GeneralResult<Merchant> me() {
        final Integer merchantId = tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
        final Merchant merchant = merchantService.require(merchantId);
        return GeneralResult.ok(merchant);
    }
}
