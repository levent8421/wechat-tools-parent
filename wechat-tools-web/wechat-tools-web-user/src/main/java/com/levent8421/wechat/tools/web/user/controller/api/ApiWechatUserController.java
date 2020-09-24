package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.resource.MerchantResourceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2020/9/24 0:01
 * Class Name :[ApiWechatUserController]
 * <p>
 * 微信用户相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/user")
public class ApiWechatUserController extends AbstractUserController {
    private final WechatUserService wechatUserService;
    private final TokenDataHolder tokenDataHolder;
    private final MerchantService merchantService;
    private final MerchantResourceService merchantResourceService;

    public ApiWechatUserController(WechatUserService wechatUserService,
                                   TokenDataHolder tokenDataHolder,
                                   MerchantService merchantService,
                                   MerchantResourceService merchantResourceService) {
        this.wechatUserService = wechatUserService;
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
        this.merchantResourceService = merchantResourceService;
    }

    /**
     * 当前用户信息
     *
     * @return GR
     */
    @GetMapping("/_me")
    public GeneralResult<WechatUser> currentUser() {
        final WechatUser user = requireUser(tokenDataHolder, wechatUserService);
        final Merchant merchant = merchantService.require(user.getMerchantId());
        merchantResourceService.resolveStaticPath(merchant);
        user.setMerchant(merchant);
        return GeneralResult.ok(user);
    }
}
