package com.levent8421.wechat.tools.model.service.combine;

/**
 * Create By leven ont 2020/9/20 3:44
 * Class Name :[MerchantAppService]
 * <p>
 * 商户APP组合业务组件
 *
 * @author leven
 */
public interface MerchantAppService {
    /**
     * 取消商户所有的默认应用
     *
     * @param merchantId 商户ID
     */
    void cancelMerchantDefaultApp(Integer merchantId);
}
