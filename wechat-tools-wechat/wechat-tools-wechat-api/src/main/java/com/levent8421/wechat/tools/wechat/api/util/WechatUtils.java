package com.levent8421.wechat.tools.wechat.api.util;

import com.levent8421.wechat.tools.wechat.api.vo.WechatOauthToken;
import org.apache.commons.lang3.StringUtils;

/**
 * Create By leven ont 2020/9/8 23:19
 * Class Name :[WechatUtils]
 * <p>
 * 微信相关工具
 *
 * @author leven
 */
public class WechatUtils {
    /**
     * 判断微信OAuth 是否授权成功
     *
     * @param token token
     * @return success?
     */
    public static boolean isSuccess(WechatOauthToken token) {
        return token != null && !StringUtils.isBlank(token.getOpenId());
    }
}
