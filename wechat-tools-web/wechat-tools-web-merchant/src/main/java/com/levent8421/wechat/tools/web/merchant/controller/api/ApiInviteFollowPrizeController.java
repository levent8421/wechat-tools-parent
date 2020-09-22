package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowPrizeService;
import com.levent8421.wechat.tools.resource.InviteFollowPrizeResourceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:45
 * Class Name: ApiInviteFollowPrizeController
 * Author: Levent8421
 * Description:
 * 关注抽奖应用奖品相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/invite-follow-prize")
public class ApiInviteFollowPrizeController extends AbstractMerchantController {
    private final InviteFollowPrizeService inviteFollowPrizeService;
    private final InviteFollowAppService inviteFollowAppService;
    private final TokenDataHolder tokenDataHolder;
    private final InviteFollowPrizeResourceService inviteFollowPrizeResourceService;

    public ApiInviteFollowPrizeController(InviteFollowPrizeService inviteFollowPrizeService,
                                          InviteFollowAppService inviteFollowAppService,
                                          TokenDataHolder tokenDataHolder,
                                          InviteFollowPrizeResourceService inviteFollowPrizeResourceService) {
        this.inviteFollowPrizeService = inviteFollowPrizeService;
        this.inviteFollowAppService = inviteFollowAppService;
        this.tokenDataHolder = tokenDataHolder;
        this.inviteFollowPrizeResourceService = inviteFollowPrizeResourceService;
    }

    /**
     * Find prizes by app id
     *
     * @param appId app id
     * @return GR
     */
    @GetMapping("/_by-app")
    private GeneralResult<List<InviteFollowPrize>> findByApp(@RequestParam("appId") Integer appId) {
        final InviteFollowApp app = inviteFollowAppService.require(appId);
        checkPermission(tokenDataHolder, app.getMerchantId());
        final List<InviteFollowPrize> prizes = inviteFollowPrizeService.findByApp(appId);
        inviteFollowPrizeResourceService.resolveStaticPath(prizes);
        return GeneralResult.ok(prizes);
    }

    /**
     * 创建奖品
     *
     * @param param 参数
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<InviteFollowPrize> createPrize(@RequestBody InviteFollowPrize param) {
        final InviteFollowPrize prize = new InviteFollowPrize();
        checkAndCopyCreateParam(param, prize);
        final Integer appId = prize.getInviteFollowAppId();
        final InviteFollowApp app = inviteFollowAppService.require(appId);
        checkPermission(tokenDataHolder, app.getMerchantId());

        final InviteFollowPrize resPrize = inviteFollowPrizeService.create(prize);
        return GeneralResult.ok(resPrize);
    }

    private void checkAndCopyCreateParam(InviteFollowPrize param, InviteFollowPrize prize) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "No params!");
        notEmpty(param.getName(), error, "请输入奖品名称");
        notNull(param.getTotalStock(), error, "请输入总数");
        notNull(param.getWinningRate(), error, "请输入中奖率");
        notNull(param.getInviteFollowAppId(), error, "APP未指定！");

        prize.setName(param.getName());
        prize.setTotalStock(param.getTotalStock());
        prize.setWinningRate(param.getWinningRate());
        prize.setInviteFollowAppId(param.getInviteFollowAppId());
    }

    /**
     * 上传奖品图片
     *
     * @param id        奖品ID
     * @param imageFile 图片文件
     * @return GR
     */
    @PostMapping("/{id}/image")
    public GeneralResult<InviteFollowPrize> uploadImage(@PathVariable("id") Integer id,
                                                        MultipartFile imageFile) {
        notEmpty(imageFile, BadRequestException.class, "图片未上传");
        final InviteFollowPrize prize = inviteFollowPrizeService.require(id);
        final InviteFollowApp app = inviteFollowAppService.require(prize.getInviteFollowAppId());
        checkPermission(tokenDataHolder, app.getMerchantId());

        final String imageFileName = inviteFollowPrizeResourceService.saveImage(imageFile);
        prize.setImage(imageFileName);
        final InviteFollowPrize resPrize = inviteFollowPrizeService.updateById(prize);
        return GeneralResult.ok(resPrize);
    }
}
