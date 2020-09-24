package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:04
 * Class Name: InviteFollowAppService
 * Author: Levent8421
 * Description:
 * Invite follow app service
 *
 * @author Levent8421
 */
public interface InviteFollowAppService extends AbstractService<InviteFollowApp> {
    /**
     * Find invite follow app by merchant id
     *
     * @param merchantId merchant id
     * @return invite follow apps
     */
    List<InviteFollowApp> findByMerchant(Integer merchantId);

    /**
     * 创建APP
     *
     * @param app      app
     * @param merchant 商户
     * @return app
     */
    InviteFollowApp createApp(InviteFollowApp app, Merchant merchant);

    /**
     * 取消商户默认邀请关注应用
     *
     * @param merchantId 商户ID
     */
    void cancelMerchantDefaultApp(Integer merchantId);

    /**
     * 设置app是否为默认APP
     *
     * @param app        app
     * @param defaultApp is default
     * @return App
     */
    InviteFollowApp setDefaultAppFlag(InviteFollowApp app, Boolean defaultApp);

    /**
     * Find app by state and merchant
     *
     * @param merchantId merchant id
     * @param state      state  code
     * @return apps
     */
    List<InviteFollowApp> findByMerchantAndState(Integer merchantId, Integer state);
}
