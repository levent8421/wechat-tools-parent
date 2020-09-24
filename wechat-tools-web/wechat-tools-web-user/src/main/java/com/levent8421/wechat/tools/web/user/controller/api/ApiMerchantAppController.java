package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.context.MerchantAppState;
import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.vo.MerchantApps;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2020/9/24 22:04
 * Class Name :[ApiMerchantAppController]
 * <p>
 * 商户应用相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/apps")
public class ApiMerchantAppController extends AbstractUserController {
    private final MerchantAppService merchantAppService;
    private final TokenDataHolder tokenDataHolder;

    public ApiMerchantAppController(MerchantAppService merchantAppService,
                                    TokenDataHolder tokenDataHolder) {
        this.merchantAppService = merchantAppService;
        this.tokenDataHolder = tokenDataHolder;
    }

    /**
     * 获取当前商户的所有上线APP
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<MerchantApps> appsForCurrentMerchant() {
        final Integer merchantId = requireMerchantId(tokenDataHolder);
        final MerchantApps apps = merchantAppService.findByMerchantWithState(merchantId, MerchantAppState.STATE_AVAILABLE);
        return GeneralResult.ok(apps);
    }
}
