package com.levent8421.wechat.tools.web.user.vo;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import lombok.Data;

/**
 * Create By leven ont 2020/9/24 0:23
 * Class Name :[UserTokenVo]
 * <p>
 * 用户令牌Value Object
 *
 * @author leven
 */
@Data
public class UserTokenVo {
    /**
     * 令牌
     */
    private String token;
    /**
     * 用户信息
     */
    private WechatUser wechatUser;
    /**
     * 商户信息
     */
    private Merchant merchant;
}
