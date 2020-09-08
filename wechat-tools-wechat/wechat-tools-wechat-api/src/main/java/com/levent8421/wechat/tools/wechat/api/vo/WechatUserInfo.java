package com.levent8421.wechat.tools.wechat.api.vo;

import lombok.Data;

import java.util.List;

/**
 * Create By leven ont 2020/9/8 23:41
 * Class Name :[WechatUserInfo]
 * <p>
 * 微信用户信息
 *
 * @author leven
 */
@Data
public class WechatUserInfo {
    /**
     * OPEN ID
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 头像地址
     */
    private String headimgurl;
    /**
     * 特权
     */
    private List<String> privilege;
    /**
     * 联合ID
     */
    private String unionid;
}
