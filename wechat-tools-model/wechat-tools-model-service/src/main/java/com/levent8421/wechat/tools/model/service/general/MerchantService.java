package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 0:46
 * Class Name: MerchantService
 * Author: Levent8421
 * Description:
 * 商户业务组件
 *
 * @author Levent8421
 */
public interface MerchantService extends AbstractService<Merchant> {
    /**
     * 创建新商户
     *
     * @param merchant 参数
     * @return 创建结果
     */
    Merchant create(Merchant merchant);

    /**
     * 通过登录名查找商户
     *
     * @param loginName 登录名
     * @return 商户
     */
    Merchant findByLoginName(String loginName);

    /**
     * 绑定商户微信APP ID
     *
     * @param merchant     商户
     * @param wechatAppId  微信APP ID
     * @param wechatSecret 微信secret
     * @return Merchant
     */
    Merchant bindWechatAppId(Merchant merchant, String wechatAppId, String wechatSecret);

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @return merchant
     */
    Merchant login(String loginName, String password);

    /**
     * 重置密码
     *
     * @param merchant    商户
     * @param password    原密码
     * @param newPassword 新密码
     */
    void resetPassword(Merchant merchant, String password, String newPassword);

    /**
     * Find Merchant By SerialNumber
     *
     * @param sn sn
     * @return Merchant or null
     */
    Merchant findBySn(String sn);
}
