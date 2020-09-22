package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.model.service.general.InviteFollowPrizeService;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:45
 * Class Name: ApiInvteFollowPrizeController
 * Author: Levent8421
 * Description:
 * 关注抽奖应用奖品相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/invite-follow-prize")
public class ApiInvteFollowPrizeController extends AbstractMerchantController {
    private final InviteFollowPrizeService inviteFollowPrizeService;

    public ApiInvteFollowPrizeController(InviteFollowPrizeService inviteFollowPrizeService) {
        this.inviteFollowPrizeService = inviteFollowPrizeService;
    }
}
