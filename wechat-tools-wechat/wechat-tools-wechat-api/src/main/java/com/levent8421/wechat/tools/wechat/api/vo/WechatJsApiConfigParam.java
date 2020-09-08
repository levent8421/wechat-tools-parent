package com.levent8421.wechat.tools.wechat.api.vo;

import com.levent8421.wechat.tools.wechat.api.WechatConstants;
import lombok.Data;

import java.util.List;

/**
 * Create By leven ont 2020/9/8 22:04
 * Class Name :[WechatJsApiConfigParam]
 * <p>
 * 微信JS API配置参数
 *
 * @author leven
 */
@Data
public class WechatJsApiConfigParam {
    /**
     * 允许调试
     */
    private boolean debug = WechatConstants.ENABLE_JS_API_DEBUG;
    /**
     * APP ID
     */
    private String appId;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 签名
     */
    private String signature;
    /**
     * 申请的API 列表
     */
    private List<String> jsApiList;
}
