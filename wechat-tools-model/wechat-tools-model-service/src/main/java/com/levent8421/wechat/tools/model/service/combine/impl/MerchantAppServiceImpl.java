package com.levent8421.wechat.tools.model.service.combine.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.model.service.vo.MerchantApps;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By leven ont 2020/9/20 3:46
 * Class Name :[MerchantAppServiceImpl]
 * <p>
 * 商户APP业务组件实现
 *
 * @author leven
 */
@Service
public class MerchantAppServiceImpl implements MerchantAppService {
    private final InviteFollowAppService inviteFollowAppService;

    public MerchantAppServiceImpl(InviteFollowAppService inviteFollowAppService) {
        this.inviteFollowAppService = inviteFollowAppService;
    }

    @Override
    public void cancelMerchantDefaultApp(Integer merchantId) {
        inviteFollowAppService.cancelMerchantDefaultApp(merchantId);
    }

    @Override
    public MerchantApps findByMerchant(Integer merchantId) {
        final MerchantApps apps = new MerchantApps();
        apps.setInviteFollowApps(inviteFollowAppService.findByMerchant(merchantId));
        return apps;
    }

    @Override
    public MerchantApps findByMerchantWithState(Integer merchantId, Integer state) {
        final MerchantApps apps = new MerchantApps();
        final List<InviteFollowApp> inviteFollowApps = inviteFollowAppService.findByMerchantAndState(merchantId, state);
        apps.setInviteFollowApps(inviteFollowApps);
        return apps;
    }
}
