package com.levent8421.wechat.tools.model.service.authorization;

/**
 * Create By leven ont 2020/9/20 3:16
 * Class Name :[MerchantAuthorizationUtils]
 * <p>
 * 商户权限工具类
 *
 * @author leven
 */
public class MerchantAuthorizationUtils {
    /**
     * 检查商户是否拥有权限
     *
     * @param authString 商户权限描述
     * @param authName   权限名称
     * @return 拥有权限
     */
    public static boolean checkPermission(String authString, String authName) {
        return true;
    }
}
