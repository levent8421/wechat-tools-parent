package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.StaticResourceService;
import com.levent8421.wechat.tools.resource.WechatVerifyFileService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 20:07
 * Class Name: WechatVerifyFileServiceImpl
 * Author: Levent8421
 * Description:
 * 微信校验文件相关操作实现
 *
 * @author Levent8421
 */
@Component
public class WechatVerifyFileServiceImpl implements WechatVerifyFileService {
    private static final String FILE_SUFFIX = ".txt";
    private static final int MAX_FILE_SIZE = 4 * 1024;
    private final ResourcePathService resourcePathService;
    private final ResourceConfigurationProperties resourceConfigurationProperties;
    private final StaticResourceService staticResourceService;
    private final String filePath;

    public WechatVerifyFileServiceImpl(ResourcePathService resourcePathService,
                                       StaticResourceService staticResourceService,
                                       ResourceConfigurationProperties resourceConfigurationProperties) {
        this.resourcePathService = resourcePathService;
        this.staticResourceService = staticResourceService;
        this.resourceConfigurationProperties = resourceConfigurationProperties;
        this.filePath = path();
    }

    private String path() {
        return resourcePathService.joinPath(
                resourcePathService.getRootPath(),
                resourceConfigurationProperties.getWechatVerifyFilePath());
    }

    @Override
    public String saveFile(MultipartFile file, Integer merchantId) throws IOException {
        final String fileName = file.getName();
        if (!fileName.endsWith(FILE_SUFFIX)) {
            throw new BadRequestException("请上传txt文件");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("请上传4k以内文件");
        }
        final String folder = String.valueOf(merchantId);
        final String path = resourcePathService.joinPath(filePath, folder);
        final File targetFile = new File(path, fileName);
        file.transferTo(targetFile);
        return fileName;
    }
}
