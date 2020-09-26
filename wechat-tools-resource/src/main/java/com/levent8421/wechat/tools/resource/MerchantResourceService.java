package com.levent8421.wechat.tools.resource;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create By leven ont 2020/9/14 20:31
 * Class Name :[MerchantResourceService]
 * <p>
 * 商户相关静态资源处理逻辑定义
 *
 * @author leven
 */
public interface MerchantResourceService extends EntityResourceService<Merchant> {
    /**
     * 保存logo文件
     *
     * @param logoFile logo文件
     * @return 文件名
     */
    String saveLogo(MultipartFile logoFile);

    /**
     * Generate wechat authorization url qrcode
     *
     * @param sn      sn
     * @param baseUrl Base URL
     * @return qrCode image file url
     */
    String generateQrCode(String baseUrl, String sn);

    /**
     * 获取商户入口地址
     *
     * @param baseUrl 基础路径
     * @param sn      商户SN
     * @return home link
     */
    String getHomeUrl(String baseUrl, String sn);
}
