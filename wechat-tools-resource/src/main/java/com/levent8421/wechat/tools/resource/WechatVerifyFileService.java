package com.levent8421.wechat.tools.resource;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 20:07
 * Class Name: WechatVerifyFileService
 * Author: Levent8421
 * Description:
 * 微信校验文件相关资源操作封装
 *
 * @author Levent8421
 */
public interface WechatVerifyFileService {
    /**
     * 保存校验文件
     *
     * @param file       文件
     * @param merchantId 商户ID
     * @return filename
     * @throws IOException e
     */
    String saveFile(MultipartFile file, Integer merchantId) throws IOException;
}
