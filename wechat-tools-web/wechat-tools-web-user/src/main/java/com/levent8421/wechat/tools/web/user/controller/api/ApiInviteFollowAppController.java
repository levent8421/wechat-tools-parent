package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowPrizeService;
import com.levent8421.wechat.tools.resource.InviteFollowAppResourceService;
import com.levent8421.wechat.tools.resource.InviteFollowPrizeResourceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.controller.AbstractUserController;
import com.levent8421.wechat.tools.web.user.vo.InviteFollowDrawParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create By leven ont 2020/9/24 23:09
 * Class Name :[ApiInviteFollowAppController]
 * <p>
 * 关注邀请APP相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/invite-follow-app")
public class ApiInviteFollowAppController extends AbstractUserController {
    private final InviteFollowAppService inviteFollowAppService;
    private final InviteFollowAppResourceService inviteFollowAppResourceService;
    private final InviteFollowPrizeService inviteFollowPrizeService;
    private final InviteFollowPrizeResourceService inviteFollowPrizeResourceService;
    private final TokenDataHolder tokenDataHolder;

    public ApiInviteFollowAppController(InviteFollowAppService inviteFollowAppService,
                                        InviteFollowAppResourceService inviteFollowAppResourceService,
                                        InviteFollowPrizeService inviteFollowPrizeService,
                                        InviteFollowPrizeResourceService inviteFollowPrizeResourceService,
                                        TokenDataHolder tokenDataHolder) {
        this.inviteFollowAppService = inviteFollowAppService;
        this.inviteFollowAppResourceService = inviteFollowAppResourceService;
        this.inviteFollowPrizeService = inviteFollowPrizeService;
        this.inviteFollowPrizeResourceService = inviteFollowPrizeResourceService;
        this.tokenDataHolder = tokenDataHolder;
    }

    /**
     * 获取APP详细信息
     *
     * @param id app id
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<InviteFollowApp> appInfo(@PathVariable("id") Integer id) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermissionByMerchant(tokenDataHolder, app.getMerchantId());

        inviteFollowAppResourceService.resolveStaticPath(app);
        final List<InviteFollowPrize> prizes = inviteFollowPrizeService.findAvailableByAppId(app.getId());
        inviteFollowPrizeResourceService.resolveStaticPath(prizes);
        app.setPrizes(prizes);
        return GeneralResult.ok(app);
    }

    /**
     * 抽奖
     *
     * @param id appId
     * @return GR
     */
    @PostMapping("/{id}/_draw")
    public GeneralResult<Void> draw(@PathVariable("id") Integer id,
                                    @RequestBody InviteFollowDrawParam param) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermissionByMerchant(tokenDataHolder, app.getMerchantId());
        final List<InviteFollowPrize> prizes = inviteFollowPrizeService.findAvailableByAppId(id);
        final InviteFollowPrize prize = inviteFollowPrizeService.draw(prizes);
        if (prize == null) {
            return GeneralResult.ok();
        }
        return GeneralResult.ok();
    }
}
