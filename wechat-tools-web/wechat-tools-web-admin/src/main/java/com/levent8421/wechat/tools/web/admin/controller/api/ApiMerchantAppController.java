package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.commons.vo.MerchantApps;
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
    private final InviteFollowAppService inviteFollowAppService;

    public ApiMerchantAppController(InviteFollowAppService inviteFollowAppService) {
        this.inviteFollowAppService = inviteFollowAppService;
    }

    /**
     * 查询商户的全部应用
     *
     * @param merchantId 商户ID
     * @return GR
     */
    @GetMapping("/_by-merchant")
    public GeneralResult<MerchantApps> findAppsByMerchant(@RequestParam("merchantId") Integer merchantId) {
        final MerchantApps apps = new MerchantApps();
        apps.setInviteFollowApps(inviteFollowAppService.findByMerchant(merchantId));
        return GeneralResult.ok(apps);
    }
}
