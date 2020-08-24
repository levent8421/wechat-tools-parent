package com.levent8421.wechat.tools.wechat.token.fetcher.impl;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 1:52
 * Class Name: StandardWechatTokenConfig
 * Author: Levent8421
 * Description:
 * 标准token获取策略配置参数
 *
 * @author Levent8421
 */
@Data
public class StandardWechatTokenConfig {
    /**
     * APP ID
     */
    private String appId;
    /**
     * APP Secret
     */
    private String secret;
}
