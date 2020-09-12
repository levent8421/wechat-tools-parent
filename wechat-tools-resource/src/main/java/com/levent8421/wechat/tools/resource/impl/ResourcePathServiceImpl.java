package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 19:44
 * Class Name: ResourcePathServiceImpl
 * Author: Levent8421
 * Description:
 * 静态资源路径相关操作实现
 *
 * @author Levent8421
 */
@Component
public class ResourcePathServiceImpl implements ResourcePathService {
    private static final char DELIMITER = '/';
    private final ResourceConfigurationProperties resourceConfigurationProperties;

    public ResourcePathServiceImpl(ResourceConfigurationProperties resourceConfigurationProperties) {
        this.resourceConfigurationProperties = resourceConfigurationProperties;
    }

    @Override
    public String getServer() {
        return resourceConfigurationProperties.getServer();
    }

    @Override
    public String getRootPath() {
        return resourceConfigurationProperties.getRootPath();
    }

    @Override
    public String getWechatVerifyFilePath() {
        return joinPath(getRootPath(), resourceConfigurationProperties.getWechatVerifyFilePath());
    }

    @Override
    public String getWechatVerifyFileUrl() {
        return joinPath(getServer(), resourceConfigurationProperties.getWechatVerifyFilePath());
    }

    @Override
    public String joinPath(String... paths) {
        if (paths.length <= 0) {
            return "";
        }
        if (paths.length == 1) {
            return paths[0];
        }
        final String firstPath = paths[0];
        final StringBuilder sb = new StringBuilder(firstPath);
        for (int i = 1; i < paths.length; i++) {
            final String path = paths[i];
            final char lastChar = sb.charAt(sb.length() - 1);
            if (lastChar != DELIMITER) {
                sb.append(DELIMITER);
            }
            sb.append(path);
        }
        return sb.toString();
    }
}
