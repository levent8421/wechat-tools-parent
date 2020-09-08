package com.levent8421.wechat.tools.wechat.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Create By leven ont 2020/9/8 23:17
 * Class Name :[WechatOauthToken]
 * <p>
 * 微信OAuth token
 *
 * @author leven
 */
@Data
public class WechatOauthToken {
    /**
     * Access Token
     */
    @JSONField(name = "access_token")
    private String accessToken;
    /**
     * 过期时间
     */
    @JSONField(name = "expires_in")
    private String expiresIn;
    /**
     * 刷新Token
     */
    @JSONField(name = "refresh_token")
    private String refreshToken;
    /**
     * 用户OpenId
     */
    @JSONField(name = "openid")
    private String openId;
    /**
     * 范围
     */
    @JSONField(name = "scope")
    private String scope;
}
