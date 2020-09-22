package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import com.levent8421.wechat.tools.web.commons.vo.MerchantApps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    /**
     * 转发关注APP相关业务组件
     */
    private final InviteFollowAppService inviteFollowAppService;
    private final TokenDataHolder tokenDataHolder;

    public ApiMerchantAppController(InviteFollowAppService inviteFollowAppService, TokenDataHolder tokenDataHolder) {
        this.inviteFollowAppService = inviteFollowAppService;
        this.tokenDataHolder = tokenDataHolder;
    }

    /**
     * 当前商户的所有应用
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<MerchantApps> myApp() {
        final Integer merchantId = requireCurrentMerchantId(tokenDataHolder);
        final List<InviteFollowApp> inviteFollowApps = inviteFollowAppService.findByMerchant(merchantId);

        final MerchantApps merchantApps = new MerchantApps();
        merchantApps.setInviteFollowApps(inviteFollowApps);
        return GeneralResult.ok(merchantApps);
    }
}
