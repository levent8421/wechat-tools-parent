package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.dto.LinkImage;
import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.resource.InviteFollowAppResourceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final InviteFollowAppResourceService inviteFollowAppResourceService;

    public ApiInviteFollowAppController(InviteFollowAppService inviteFollowAppService,
                                        TokenDataHolder tokenDataHolder,
                                        MerchantService merchantService,
                                        MerchantAppService merchantAppService,
                                        InviteFollowAppResourceService inviteFollowAppResourceService) {
        this.inviteFollowAppService = inviteFollowAppService;
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
        this.merchantAppService = merchantAppService;
        this.inviteFollowAppResourceService = inviteFollowAppResourceService;
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

    /**
     * Find One App
     *
     * @param id id
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<InviteFollowApp> findOneApp(@PathVariable("id") Integer id) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        inviteFollowAppResourceService.resolveStaticPath(app);
        return GeneralResult.ok(app);
    }

    /**
     * 设置应用基本信息
     *
     * @param id    ID
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/_base-info")
    public GeneralResult<InviteFollowApp> updateAppBaseInfo(@PathVariable("id") Integer id,
                                                            @RequestBody InviteFollowApp param) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkAndCopyUpdateBaseInfoParam(param, app);
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        return GeneralResult.ok(resApp);
    }

    private void checkAndCopyUpdateBaseInfoParam(InviteFollowApp param, InviteFollowApp app) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "No Params!");
        notEmpty(param.getTitle(), error, "请输入标题");
        notEmpty(param.getThemeColor(), error, "请选择主题文字");

        app.setTitle(param.getTitle());
        app.setSubtitle(param.getSubtitle());
        app.setFooterText(param.getFooterText());
        app.setThemeColor(param.getThemeColor());
        app.setRulesText(param.getRulesText());
        app.setDeadline(param.getDeadline());
        app.setPhoneRequired(param.getPhoneRequired());
    }

    /**
     * 设置顶部图片
     *
     * @param id              id
     * @param bannerImageFile file
     * @return GR
     */
    @PostMapping("/{id}/banner-image")
    public GeneralResult<InviteFollowApp> setBannerImage(@PathVariable("id") Integer id, MultipartFile bannerImageFile) {
        notEmpty(bannerImageFile, BadRequestException.class, "未上传图片");
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermission(app.getMerchantId());
        final String imageFileName = inviteFollowAppResourceService.saveBannerImage(bannerImageFile);
        app.setBannerImage(imageFileName);
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        inviteFollowAppResourceService.resolveStaticPath(resApp);
        return GeneralResult.ok(resApp);
    }

    /**
     * 设置抽奖按钮图片
     *
     * @param id              id
     * @param buttonImageFile file
     * @return GR
     */
    @PostMapping("/{id}/button-image")
    public GeneralResult<InviteFollowApp> setButtonImage(@PathVariable("id") Integer id, MultipartFile buttonImageFile) {
        notEmpty(buttonImageFile, BadRequestException.class, "未上传图片");
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermission(app.getMerchantId());
        final String imageFileName = inviteFollowAppResourceService.saveButtonImage(buttonImageFile);
        app.setButtonImage(imageFileName);
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        inviteFollowAppResourceService.resolveStaticPath(resApp);
        return GeneralResult.ok(resApp);
    }

    private void checkPermission(Integer merchantId) {
        final Integer myId = requireCurrentMerchantId(tokenDataHolder);
        if (!Objects.equals(merchantId, myId)) {
            throw new BadRequestException("您无权操作该应用！");
        }
    }

    /**
     * 上传新图片
     *
     * @param imageFile image file
     * @param url       url
     * @return GR
     */
    @PostMapping("/{id}/_append-image")
    public GeneralResult<InviteFollowApp> appendImage(@PathVariable("id") Integer id,
                                                      MultipartFile imageFile,
                                                      @RequestParam("url") String url) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermission(app.getMerchantId());

        final List<LinkImage> images = inviteFollowAppResourceService.appendImage(app.getImagesJson(), imageFile, url);
        app.setImagesJson(JSON.toJSONString(images));
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        resApp.setImages(images);
        return GeneralResult.ok(resApp);
    }

    /**
     * 删除图片
     *
     * @param id    app id
     * @param index image index
     * @return GR
     */
    @DeleteMapping("/{id}/image")
    public GeneralResult<InviteFollowApp> removeImage(@PathVariable("id") Integer id,
                                                      @RequestParam("index") Integer index) {
        final InviteFollowApp app = inviteFollowAppService.require(id);
        checkPermission(app.getMerchantId());

        final List<LinkImage> images = inviteFollowAppResourceService.removeImage(app.getImagesJson(), index);
        app.setImagesJson(JSON.toJSONString(images));
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        resApp.setImages(images);
        return GeneralResult.ok(resApp);
    }
}
