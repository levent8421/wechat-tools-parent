package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.vo.MerchantApps;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2020/9/22 23:51
 * Class Name :[ApiMerchantAppController]
 * <p>
 * 商户应用相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/merchant-app")
public class ApiMerchantAppController extends AbstractController {
    private final MerchantAppService merchantAppService;

    public ApiMerchantAppController(MerchantAppService merchantAppService) {
        this.merchantAppService = merchantAppService;
    }

    /**
     * 查询商户的全部应用
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/_by-merchant")
    public GeneralResult<MerchantApps> findAppsByMerchant(@RequestParam("merchantId") Integer merchantId) {
        final MerchantApps apps = merchantAppService.findByMerchant(merchantId);
        return GeneralResult.ok(apps);
    }
}
