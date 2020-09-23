package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.WechatVerifyFileService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import com.levent8421.wechat.tools.resource.dto.VerifyFileInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private final String filePath;
    private final Map<String, VerifyFileInfo> copiedFiles = new HashMap<>(128);

    public WechatVerifyFileServiceImpl(ResourcePathService resourcePathService,
                                       ResourceConfigurationProperties resourceConfigurationProperties) {
        this.resourcePathService = resourcePathService;
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
        final String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new BadRequestException("获取文件名失败");
        }
        if (!fileName.endsWith(FILE_SUFFIX)) {
            throw new BadRequestException("请上传txt文件");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("请上传4k以内文件");
        }
        final String folder = String.valueOf(merchantId);
        final String path = resourcePathService.joinPath(filePath, folder);
        final File pathFile = new File(path);
        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                throw new InternalServerErrorException("Error on create dir : " + path);
            }
        }
        final File targetFile = new File(path, fileName);
        file.transferTo(targetFile);
        return fileName;
    }

    @Override
    public void enableFile(Integer merchantId, String fileName) {
        final String srcPath = resourcePathService.joinPath(filePath, String.valueOf(merchantId), fileName);
        final File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new BadRequestException("文件[" + srcPath + "]不存在");
        }
        final String rootPath = resourceConfigurationProperties.getWechatVerifyFileRootPath();
        final File rootPathFile = new File(rootPath);
        if (!rootPathFile.isDirectory()) {
            if (rootPathFile.exists()) {
                throw new InternalServerErrorException("Can not create dir[" + rootPathFile.getAbsolutePath() + "], there is a file with same name");
            }
            if (!rootPathFile.mkdirs()) {
                throw new InternalServerErrorException("Can not create dir [" + rootPathFile.getAbsolutePath() + "]!");
            }
        }
        final File destFile = new File(rootPathFile, fileName);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on copy file", e);
        }
        final VerifyFileInfo fileInfo = new VerifyFileInfo(destFile, System.currentTimeMillis());
        copiedFiles.put(fileName, fileInfo);
    }

    @Override
    public void cleanFiles() {
        final long now = System.currentTimeMillis();
        copiedFiles.values()
                .stream()
                .filter(file -> file.isExpired(now))
                .forEach(VerifyFileInfo::delete);
        copiedFiles.clear();
    }

    @Override
    public List<String> files(Integer merchantId) {
        final File pathFile = new File(filePath, String.valueOf(merchantId));
        if (!pathFile.exists()) {
            return Collections.emptyList();
        }
        final String[] files = pathFile.list();
        if (files == null || files.length <= 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(files);
    }
}
