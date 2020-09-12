package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.StaticResourceService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 19:55
 * Class Name: StaticResourceServiceImpl
 * Author: Levent8421
 * Description:
 * 静态资源相关操作实现
 *
 * @author Levent8421
 */
@Component
public class StaticResourceServiceImpl implements StaticResourceService {
    private final ResourcePathService resourcePathService;

    public StaticResourceServiceImpl(ResourcePathService resourcePathService) {
        this.resourcePathService = resourcePathService;
    }

    @Override
    public String saveFileAsOriginName(MultipartFile file, String path) throws IOException {
        final File targetFile = new File(path, file.getName());
        file.transferTo(targetFile);
        return file.getName();
    }
}
