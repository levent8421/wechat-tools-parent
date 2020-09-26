package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.config.WebsiteConfigurationProperties;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.resource.MerchantResourceService;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.commons.vo.QrCodeInfo;
import com.levent8421.wechat.tools.web.commons.vo.ResetPasswordParam;
import com.levent8421.wechat.tools.web.merchant.controller.AbstractMerchantController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

/**
 * Create By leven ont 2020/9/9 23:19
 * Class Name :[ApiMerchantController]
 * <p>
 * 商户相关API数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/merchant")
public class ApiMerchantController extends AbstractMerchantController {
    private final TokenDataHolder tokenDataHolder;
    private final MerchantService merchantService;
    private final MerchantResourceService merchantResourceService;
    private final WebsiteConfigurationProperties websiteConfigurationProperties;

    public ApiMerchantController(TokenDataHolder tokenDataHolder,
                                 MerchantService merchantService,
                                 MerchantResourceService merchantResourceService,
                                 WebsiteConfigurationProperties websiteConfigurationProperties) {
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
        this.merchantResourceService = merchantResourceService;
        this.websiteConfigurationProperties = websiteConfigurationProperties;
    }


    /**
     * 获取当前登录的商户
     *
     * @return GR
     */
    @GetMapping("/_me")
    public GeneralResult<Merchant> me() {
        final Merchant merchant = requireCurrentMerchant(merchantService, tokenDataHolder);
        merchantResourceService.resolveStaticPath(merchant);
        return GeneralResult.ok(merchant);
    }

    /**
     * 配置微信信息
     *
     * @param id    id
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/_wechat-info")
    public GeneralResult<Merchant> setWechatInfo(@PathVariable("id") Integer id, @RequestBody Merchant param) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getWechatAppId(), error, "请输入微信APP_ID");
        notEmpty(param.getWechatSecret(), error, "请输入微信SECRET");

        final Merchant merchant = merchantService.require(id);
        merchant.setWechatAppId(param.getWechatAppId());
        merchant.setWechatSecret(param.getWechatSecret());
        final Merchant res = merchantService.updateById(merchant);
        return GeneralResult.ok(res);
    }

    /**
     * 重置当前商户的密码
     *
     * @param param param
     * @return GR
     */
    @PostMapping("/_reset-password")
    public GeneralResult<Void> resetPassword(@RequestBody ResetPasswordParam param) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getNewPassword(), error, "请输入新密码");
        notEmpty(param.getPassword(), error, "请输入原密码");

        final Merchant merchant = requireCurrentMerchant(merchantService, tokenDataHolder);
        merchantService.resetPassword(merchant, param.getPassword(), param.getNewPassword());
        return GeneralResult.ok();
    }

    /**
     * 更新当前商户信息
     *
     * @param param param
     * @return GR
     */
    @PostMapping("/_me")
    public GeneralResult<Merchant> updateMerchantInfo(@RequestBody Merchant param) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getName(), error, "请输入商户名");
        final Merchant merchant = requireCurrentMerchant(merchantService, tokenDataHolder);
        merchant.setName(param.getName());
        final Merchant res = merchantService.updateById(merchant);
        merchantResourceService.resolveStaticPath(res);
        return GeneralResult.ok(res);
    }

    /**
     * 上传商户LOGO
     *
     * @param file logo file
     * @return GR
     */
    @PostMapping("/logo")
    public GeneralResult<Merchant> uploadLogoFile(MultipartFile file) {
        notEmpty(file, BadRequestException.class, "文件未上传");

        final Merchant merchant = requireCurrentMerchant(merchantService, tokenDataHolder);
        final String fileName = merchantResourceService.saveLogo(file);
        merchant.setLogoPath(fileName);
        final Merchant res = merchantService.updateById(merchant);
        merchantResourceService.resolveStaticPath(merchant);
        return GeneralResult.ok(res);
    }

    /**
     * 当前商户的二维码
     *
     * @return GR
     */
    @GetMapping("/_qr-code")
    public GeneralResult<QrCodeInfo> myQrCode() {
        final Merchant merchant = getCurrentMerchant(merchantService, tokenDataHolder);
        final String baseUrl = websiteConfigurationProperties.getBaseUrl();
        final String sn = merchant.getSn();
        final String qrCode = merchantResourceService.generateQrCode(baseUrl, sn);
        final String qrCodeContent = merchantResourceService.getHomeUrl(baseUrl, sn);
        final QrCodeInfo qrCodeInfo = new QrCodeInfo();
        qrCodeInfo.setImageUrl(qrCode);
        qrCodeInfo.setContent(qrCodeContent);
        return GeneralResult.ok(qrCodeInfo);
    }
}
