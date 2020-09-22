package com.levent8421.wechat.tools.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 19:24
 * Class Name: ResourceConfigurationProperties
 * Author: Levent8421
 * Description:
 * 静态资源配置
 *
 * @author Levent8421
 */
@Data
@Component
@ConfigurationProperties(prefix = "static")
public class ResourceConfigurationProperties {
    /**
     * 静态资源根目录
     */
    private String rootPath;
    /**
     * 静态资源服务器
     */
    private String server;
    /**
     * 微信校验文件存放地址
     */
    private String wechatVerifyFilePath = "wechat-verify";
    /**
     * 商户LOGO存放路径
     */
    private String merchantLogoPath = "merchant/logo";
    /**
     * 关注邀请APP顶部图片存放路径
     */
    private String inviteFollowAppBannerImagePath = "invite-follow-app/banner";
    /**
     * 关注邀请APP抽奖按钮图片存放路径
     */
    private String inviteFollowAppButtonImagePath = "invite-follow-app/button";
    /**
     * 关注邀请APP 图片文件存放路径
     */
    private String inviteFollowAppImagePath = "invite-follow-app/image";
    /**
     * 关注邀请APP奖品图片存放路径
     */
    private String inviteFollowPrizeImagePath = "invite-follow-prize/image";
}
