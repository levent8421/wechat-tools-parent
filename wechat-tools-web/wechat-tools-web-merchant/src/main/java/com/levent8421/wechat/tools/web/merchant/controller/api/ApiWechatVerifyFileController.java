package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.model.service.config.WechatConfigurationProperties;
import com.levent8421.wechat.tools.resource.WechatVerifyFileService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;
import com.levent8421.wechat.tools.web.merchant.vo.EnableWechatVerifyFileParam;
import com.levent8421.wechat.tools.web.merchant.vo.WechatPlatformConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 20:24
 * Class Name: ApiWechatVerifyFileController
 * Author: Levent8421
 * Description:
 * 微信服务器校验文件相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/verify-file")
public class ApiWechatVerifyFileController extends AbstractController {
    private final WechatVerifyFileService wechatVerifyFileService;
    private final TokenDataHolder tokenDataHolder;
    private final WechatConfigurationProperties wechatConfigurationProperties;

    public ApiWechatVerifyFileController(WechatVerifyFileService wechatVerifyFileService,
                                         TokenDataHolder tokenDataHolder,
                                         WechatConfigurationProperties wechatConfigurationProperties) {
        this.wechatVerifyFileService = wechatVerifyFileService;
        this.tokenDataHolder = tokenDataHolder;
        this.wechatConfigurationProperties = wechatConfigurationProperties;
    }

    /**
     * 上传微信服务器校验文件
     *
     * @param file 文件
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<String> uploadVerifyFile(MultipartFile file) {
        final Integer merchantId = tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
        try {
            final String fileName = wechatVerifyFileService.saveFile(file, merchantId);
            wechatVerifyFileService.enableFile(merchantId, fileName);
            return GeneralResult.ok(fileName);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on save file!", e);
        }
    }

    /**
     * 微信公众平台配置参考
     *
     * @return GR
     */
    @GetMapping("/_wechat-platform")
    public GeneralResult<WechatPlatformConfiguration> wechatPlatformConfiguration() {
        final Integer merchantId = tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
        final List<String> files = wechatVerifyFileService.files(merchantId);

        final WechatPlatformConfiguration configuration = new WechatPlatformConfiguration();
        configuration.setVerifyFiles(files);
        configuration.setAuthDomain(wechatConfigurationProperties.getAuthorizationDomain());
        configuration.setJsApiDomain(wechatConfigurationProperties.getJsApiDomain());
        configuration.setServerIp(wechatConfigurationProperties.getServerIp());
        return GeneralResult.ok(configuration);
    }

    /**
     * 使校验文件生效
     *
     * @param param post body
     * @return GR
     */
    @PostMapping("/_enable")
    public GeneralResult<Void> enableFile(@RequestBody EnableWechatVerifyFileParam param) {
        notNull(param, BadRequestException.class, "参数为空");
        notEmpty(param.getFileName(), BadRequestException.class, "请填写文件名");
        final Integer merchantId = tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
        wechatVerifyFileService.enableFile(merchantId, param.getFileName());
        return GeneralResult.ok();
    }
}
