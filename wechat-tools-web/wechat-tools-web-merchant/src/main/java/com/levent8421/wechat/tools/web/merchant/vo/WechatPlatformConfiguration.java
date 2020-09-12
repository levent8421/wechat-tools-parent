package com.levent8421.wechat.tools.web.merchant.vo;

import lombok.Data;

import java.util.List;

/**
 * Create By leven ont 2020/9/13 0:17
 * Class Name :[WechatPlatformConfiguration]
 * <p>
 * 微信公众平台配置参数参数
 *
 * @author leven
 */
@Data
public class WechatPlatformConfiguration {
    /**
     * JS API 安全域名
     */
    private String jsApiDomain;
    /**
     * 微信授权安全域名
     */
    private String authDomain;
    /**
     * 服务器IP
     */
    private String serverIp;
    /**
     * 已经上传的校验文件
     */
    private List<String> verifyFiles;
}
