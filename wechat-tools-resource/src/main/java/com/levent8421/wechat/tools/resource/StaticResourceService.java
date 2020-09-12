package com.levent8421.wechat.tools.resource;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 19:29
 * Class Name: StaticResourceService
 * Author: Levent8421
 * Description:
 * 静态资源相关操作封装
 *
 * @author Levent8421
 */
public interface StaticResourceService {
    /**
     * 使用文件原始名称保存文件
     *
     * @param file 文件
     * @param path 路径
     * @return filename
     * @throws IOException error
     */
    String saveFileAsOriginName(MultipartFile file, String path) throws IOException;
}
