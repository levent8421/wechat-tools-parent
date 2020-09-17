package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:27
 * Class Name: ApiInviteFollowAppController
 * Author: Levent8421
 * Description:
 * 转发关注抽奖相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/invite-follow-app")
public class ApiInviteFollowAppController extends AbstractMerchantController {
    private final InviteFollowAppService inviteFollowAppService;
    private final TokenDataHolder tokenDataHolder;

    public ApiInviteFollowAppController(InviteFollowAppService inviteFollowAppService,
                                        TokenDataHolder tokenDataHolder) {
        this.inviteFollowAppService = inviteFollowAppService;
        this.tokenDataHolder = tokenDataHolder;
    }
}
