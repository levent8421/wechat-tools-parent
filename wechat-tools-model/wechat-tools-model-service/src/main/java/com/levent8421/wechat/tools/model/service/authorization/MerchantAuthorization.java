package com.levent8421.wechat.tools.model.service.authorization;

/**
 * Create By Levent8421
 * Create Time: 2020/9/8 16:17
 * Class Name: MerchantAuthorization
 * Author: Levent8421
 * Description:
 * 商户权限数据类
 *
 * @author Levent8421
 */
public class MerchantAuthorization {
    /**
     * 允许商户登录
     */
    private Boolean login;
    /**
     * 允许商户配置使用公众号功能
     */
    private Boolean wechat;
    /**
     * 允许商户使用邀请关注功能
     */
    private Boolean invite;
    /**
     * 允许商户使用固定金额支付功能
     */
    private Boolean tempPay;
    /**
     * 是否允许商户使用可变金额支付功能
     */
    private Boolean pay;
}