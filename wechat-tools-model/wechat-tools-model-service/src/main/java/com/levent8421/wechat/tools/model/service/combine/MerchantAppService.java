package com.levent8421.wechat.tools.model.service.combine;

import com.levent8421.wechat.tools.model.service.vo.MerchantApps;

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

    /**
     * 指定商户的应用
     *
     * @param merchantId merchantId
     * @return Apps
     */
    MerchantApps findByMerchant(Integer merchantId);

    /**
     * 获取指定商户指定状态的APP
     *
     * @param merchantId 商户
     * @param state      state
     * @return apps
     */
    MerchantApps findByMerchantWithState(Integer merchantId, Integer state);
}
