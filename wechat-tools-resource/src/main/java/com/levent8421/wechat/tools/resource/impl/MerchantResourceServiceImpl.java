package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.resource.MerchantResourceService;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create By leven ont 2020/9/14 20:39
 * Class Name :[MerchantResourceServiceImpl]
 * <p>
 * 商户静态资源处理器实现
 *
 * @author leven
 */
@Service
public class MerchantResourceServiceImpl extends AbstractEntityResourceService<Merchant> implements MerchantResourceService {
    private final ResourceConfigurationProperties resourceConfigurationProperties;
    private final SerialNumberGenerator fileNameGenerator = new SerialNumberGenerator("logo", "m", 5);

    protected MerchantResourceServiceImpl(ResourcePathService resourcePathService,
                                          ResourceConfigurationProperties resourceConfigurationProperties) {
        super(resourcePathService);
        this.resourceConfigurationProperties = resourceConfigurationProperties;
    }

    @Override
    public void resolveStaticPath(Merchant entity) {
        if (entity == null) {
            return;
        }
        if (isNotBlank(entity.getLogoPath())) {
            final String logoPath = withServer(resourceConfigurationProperties.getMerchantLogoPath(), entity.getLogoPath());
            entity.setLogoPath(logoPath);
        }
    }

    @Override
    public String saveLogo(MultipartFile logoFile) {
        return saveFile(fileNameGenerator, logoFile, resourceConfigurationProperties.getMerchantLogoPath());
    }
}
