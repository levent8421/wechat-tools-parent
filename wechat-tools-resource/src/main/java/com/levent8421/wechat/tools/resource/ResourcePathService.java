package com.levent8421.wechat.tools.resource;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 19:39
 * Class Name: ResourcePathService
 * Author: Levent8421
 * Description:
 * 静态资源路径相关操作封装
 *
 * @author Levent8421
 */
public interface ResourcePathService {
    /**
     * 静态资源服务器地址
     *
     * @return server url
     */
    String getServer();

    /**
     * 获取静态资源根目录
     *
     * @return root path
     */
    String getRootPath();

    /**
     * 微信校验文件地址
     *
     * @return absolute file path
     */
    String getWechatVerifyFilePath();

    /**
     * 微信验证文件url prefix
     *
     * @return url prefix
     */
    String getWechatVerifyFileUrl();

    /**
     * 组合路径
     *
     * @param paths 路径列表
     * @return 组合结果
     */
    String joinPath(String... paths);
}
