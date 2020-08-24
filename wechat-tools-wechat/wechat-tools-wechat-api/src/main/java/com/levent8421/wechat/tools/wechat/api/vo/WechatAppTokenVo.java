package com.levent8421.wechat.tools.wechat.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.levent8421.wechat.tools.commons.http.SourceJsonAware;
import lombok.Data;

import java.io.Serializable;

/**
 * Create by 郭文梁 2019/4/29 0029 17:22
 * WechatAppTokenVo
 * 微信AccessToken包装类
 *
 * @author 郭文梁
 */
@Data
public class WechatAppTokenVo implements SourceJsonAware, Serializable {
    /**
     * 刷新时间
     */
    private long refreshTime;
    /**
     * Access Token
     */
    @JSONField(name = "access_token")
    private String accessToken;
    /**
     * 过期时间
     */
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    /**
     * 源JSOn
     */
    private String sourceJson;
}
