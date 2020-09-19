package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

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
    private final MerchantService merchantService;
    private final MerchantAppService merchantAppService;

    public ApiInviteFollowAppController(InviteFollowAppService inviteFollowAppService,
                                        TokenDataHolder tokenDataHolder,
                                        MerchantService merchantService,
                                        MerchantAppService merchantAppService) {
        this.inviteFollowAppService = inviteFollowAppService;
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
        this.merchantAppService = merchantAppService;
    }

    /**
     * 当前商户的邀请关注APP
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<InviteFollowApp>> myApps() {
        final Integer merchantId = requireCurrentMerchantId(tokenDataHolder);
        final List<InviteFollowApp> apps = inviteFollowAppService.findByMerchant(merchantId);
        return GeneralResult.ok(apps);
    }

    /**
     * 创建APP
     *
     * @param param params
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<InviteFollowApp> createApp(@RequestBody InviteFollowApp param) {
        final InviteFollowApp app = new InviteFollowApp();
        checkAndCopyCreateParam(param, app);
        final Merchant merchant = requireCurrentMerchant(merchantService, tokenDataHolder);
        final InviteFollowApp resApp = inviteFollowAppService.createApp(app, merchant);
        return GeneralResult.ok(resApp);
    }

    private void checkAndCopyCreateParam(InviteFollowApp param, InviteFollowApp app) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "No Params!");
        notEmpty(param.getTitle(), error, "请输入标题！");
        notEmpty(param.getThemeColor(), error, "请选择主题颜色！");

        app.setTitle(param.getTitle());
        app.setSubtitle(param.getSubtitle());
        app.setFooterText(param.getFooterText());
        app.setThemeColor(param.getThemeColor());
    }

    /**
     * 更新默认应用状态
     *
     * @param id    ID
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/defaultApp")
    public GeneralResult<InviteFollowApp> updateDefaultAppFlag(@PathVariable("id") Integer id,
                                                               @RequestBody InviteFollowApp param) {
        notNull(param, BadRequestException.class, "No Params!");
        notNull(param.getDefaultApp(), BadRequestException.class, "请选择是否为默认应用！");
        final Integer merchantId = requireCurrentMerchantId(tokenDataHolder);
        final InviteFollowApp app = inviteFollowAppService.require(id);
        if (!Objects.equals(app.getMerchantId(), merchantId)) {
            throw new BadRequestException("您无权操作该应用！");
        }
        merchantAppService.cancelMerchantDefaultApp(merchantId);
        final InviteFollowApp resApp = inviteFollowAppService.setDefaultAppFlag(app, param.getDefaultApp());
        return GeneralResult.ok(resApp);
    }
}
