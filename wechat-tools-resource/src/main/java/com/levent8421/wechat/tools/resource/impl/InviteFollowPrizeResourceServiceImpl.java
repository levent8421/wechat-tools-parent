package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.resource.InviteFollowPrizeResourceService;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create By Levent8421
 * Create Time: 2020/9/22 18:34
 * Class Name: InviteFollowPrizeResourceServiceImpl
 * Author: Levent8421
 * Description:
 * 关注抽奖应用奖品资源业务行为实现
 *
 * @author Levent8421
 */
@Service
public class InviteFollowPrizeResourceServiceImpl extends AbstractEntityResourceService<InviteFollowPrize> implements InviteFollowPrizeResourceService {
    private final ResourceConfigurationProperties resourceConfigurationProperties;
    private final SerialNumberGenerator imageFileNameGenerator = new SerialNumberGenerator("P", "E", 5);

    public InviteFollowPrizeResourceServiceImpl(ResourcePathService resourcePathService,
                                                ResourceConfigurationProperties resourceConfigurationProperties) {
        super(resourcePathService);
        this.resourceConfigurationProperties = resourceConfigurationProperties;
    }

    @Override
    public void resolveStaticPath(InviteFollowPrize entity) {
        if (isNotBlank(entity.getImage())) {
            entity.setImage(withServer(resourceConfigurationProperties.getInviteFollowPrizeImagePath(), entity.getImage()));
        }
    }

    @Override
    public String saveImage(MultipartFile file) {
        return saveFile(imageFileNameGenerator, file, resourceConfigurationProperties.getInviteFollowPrizeImagePath());
    }
}
