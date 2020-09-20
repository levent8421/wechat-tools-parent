package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.resource.InviteFollowAppResourceService;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create By leven ont 2020/9/21 2:02
 * Class Name :[InviteFollowAppResourceServiceImpl]
 * <p>
 * 关注邀请APP资源业务组件实现
 *
 * @author leven
 */
@Component
public class InviteFollowAppResourceServiceImpl extends AbstractEntityResourceService<InviteFollowApp> implements InviteFollowAppResourceService {
    private final ResourcePathService resourcePathService;
    private final ResourceConfigurationProperties resourceConfigurationProperties;
    private SerialNumberGenerator bannerFilenameGenerator = new SerialNumberGenerator("B", "E", 5);
    private SerialNumberGenerator buttonFilenameGenerator = new SerialNumberGenerator("B", "E", 5);

    public InviteFollowAppResourceServiceImpl(ResourcePathService resourcePathService,
                                              ResourceConfigurationProperties resourceConfigurationProperties) {
        super(resourcePathService);
        this.resourcePathService = resourcePathService;
        this.resourceConfigurationProperties = resourceConfigurationProperties;
    }

    @Override
    public void resolveStaticPath(InviteFollowApp entity) {
        if (isNotBlank(entity.getBannerImage())) {
            entity.setBannerImage(
                    withServer(
                            resourceConfigurationProperties.getInviteFollowAppBannerImagePath(),
                            entity.getBannerImage()
                    )
            );
        }
        if (isNotBlank(entity.getButtonImage())) {
            entity.setButtonImage(
                    withServer(
                            resourceConfigurationProperties.getInviteFollowAppButtonImagePath(),
                            entity.getButtonImage()
                    )
            );
        }
    }

    @Override
    public String saveBannerImage(MultipartFile bannerImageFile) {
        return saveFile(bannerFilenameGenerator, bannerImageFile, resourceConfigurationProperties.getInviteFollowAppBannerImagePath());
    }

    @Override
    public String saveButtonImage(MultipartFile buttonImageFile) {
        return saveFile(buttonFilenameGenerator, buttonImageFile, resourceConfigurationProperties.getInviteFollowAppButtonImagePath());
    }
}
