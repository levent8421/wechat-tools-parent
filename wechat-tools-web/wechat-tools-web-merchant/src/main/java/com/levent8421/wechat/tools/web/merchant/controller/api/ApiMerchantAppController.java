package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.vo.MerchantApps;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:06
 * Class Name: ApiMerchantAppController
 * Author: Levent8421
 * Description:
 * 商户应用相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/app")
public class ApiMerchantAppController extends AbstractMerchantController {
    private final TokenDataHolder tokenDataHolder;
    private final MerchantAppService merchantAppService;

    public ApiMerchantAppController(TokenDataHolder tokenDataHolder,
                                    MerchantAppService merchantAppService) {
        this.tokenDataHolder = tokenDataHolder;
        this.merchantAppService = merchantAppService;
    }

    /**
     * 当前商户的所有应用
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<MerchantApps> myApp() {
        final MerchantApps apps = merchantAppService.findByMerchant(requireCurrentMerchantId(tokenDataHolder));
        return GeneralResult.ok(apps);
    }
}
