package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.entity.AbstractEntity;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.utils.CollectionUtils;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.resource.EntityResourceService;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Create By leven ont 2020/9/14 20:32
 * Class Name :[AbstractEntityResourceService]
 * <p>
 * 静态资源处理器基类
 *
 * @author leven
 */
public abstract class AbstractEntityResourceService<T extends AbstractEntity> implements EntityResourceService<T> {
    /**
     * 文件扩展名分隔符
     */
    private static final String EXTENSIONS_DELIMITER = ".";
    private final ResourcePathService resourcePathService;

    AbstractEntityResourceService(ResourcePathService resourcePathService) {
        this.resourcePathService = resourcePathService;
    }

    protected ResourcePathService getResourcePathService() {
        return resourcePathService;
    }

    /**
     * 获取带有server url的绝对路径
     *
     * @param paths 路径列表
     * @return server url
     */
    String withServer(String... paths) {
        final String server = resourcePathService.getServer();
        final List<String> pathList = new ArrayList<>();
        pathList.add(server);
        pathList.addAll(Arrays.asList(paths));
        return resourcePathService.joinPath(pathList);
    }

    /**
     * 获取带有服务器静态资源路径的服务器绝对文件路径
     *
     * @param paths 路径列表
     * @return absolute fs path
     */
    String withRootPath(String... paths) {
        final String root = resourcePathService.getRootPath();
        final List<String> pathList = new ArrayList<>();
        pathList.add(root);
        pathList.addAll(Arrays.asList(paths));
        return resourcePathService.joinPath(pathList);
    }

    /**
     * 路径不存在时创建路径
     *
     * @param path 路径名称
     * @return 路径文件
     */
    File createPathIfNotExists(String path) {
        final File pathFile = new File(path);
        if (pathFile.exists()) {
            if (!pathFile.isDirectory()) {
                throw new InternalServerErrorException("Path [" + pathFile.getAbsolutePath() + "] is not a directory!");
            }
            return pathFile;
        } else {
            if (!pathFile.mkdirs()) {
                throw new InternalServerErrorException("Error on create directory[" + pathFile.getAbsolutePath() + "]");
            }
            return pathFile;
        }
    }

    boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 扩展名
     */
    String getExtensions(String filename) {
        if (filename == null) {
            return "";
        }
        final int delimiterIndex = filename.lastIndexOf(EXTENSIONS_DELIMITER);
        if (delimiterIndex >= 0) {
            return filename.substring(delimiterIndex);
        } else {
            return "";
        }
    }

    /**
     * 生成文件名
     *
     * @param generator 文件名称生成器
     * @param file      文件
     * @return filename
     */
    String nextFilename(SerialNumberGenerator generator, MultipartFile file) {
        final String ext = getExtensions(file.getOriginalFilename());
        return generator.next() + ext;
    }

    /**
     * 创建目录
     *
     * @param paths 目录列表
     * @return path file
     */
    File createPathInRootPath(String... paths) {
        final String path = withRootPath(paths);
        return createPathIfNotExists(path);
    }

    /**
     * 保存图片
     *
     * @param filenameGenerator 文件名称生成器
     * @param file              文件
     * @param paths             路径
     * @return 文件名
     */
    String saveFile(SerialNumberGenerator filenameGenerator, MultipartFile file, String... paths) {
        final File path = createPathInRootPath(paths);
        final String filename = nextFilename(filenameGenerator, file);
        final File targetFile = new File(path, filename);
        save(file, targetFile);
        return filename;
    }

    /**
     * 保存上传的文件
     *
     * @param file       file
     * @param targetFile save target
     */
    void save(MultipartFile file, File targetFile) {
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on save file [" + targetFile.getAbsolutePath() + "]", e);
        }
    }

    @Override
    public void resolveStaticPath(T entity) {

    }

    @Override
    public void resolveStaticPath(Collection<T> entities) {
        if (CollectionUtils.isEmpry(entities)) {
            return;
        }
        for (T entity : entities) {
            resolveStaticPath(entity);
        }
    }
}
