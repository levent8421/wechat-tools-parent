package com.levent8421.wechat.tools.web.commons.vo;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/9/10 18:41
 * Class Name: ResetPasswordParam
 * Author: Levent8421
 * Description:
 * 重置密码参数
 *
 * @author Levent8421
 */
@Data
public class ResetPasswordParam {
    /**
     * 旧密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;
}
