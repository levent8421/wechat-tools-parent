package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.resource.WechatVerifyFileService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    public ApiWechatVerifyFileController(WechatVerifyFileService wechatVerifyFileService,
                                         TokenDataHolder tokenDataHolder) {
        this.wechatVerifyFileService = wechatVerifyFileService;
        this.tokenDataHolder = tokenDataHolder;
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
}
