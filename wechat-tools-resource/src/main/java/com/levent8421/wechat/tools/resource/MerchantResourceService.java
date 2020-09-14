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
}
