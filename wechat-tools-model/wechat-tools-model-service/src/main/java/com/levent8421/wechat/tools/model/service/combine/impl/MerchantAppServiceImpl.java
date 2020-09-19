package com.levent8421.wechat.tools.model.service.combine.impl;

import com.levent8421.wechat.tools.model.service.combine.MerchantAppService;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import org.springframework.stereotype.Service;

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
}
