package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowAppMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:04
 * Class Name: InviteFollowAppServiceImpl
 * Author: Levent8421
 * Description:
 * Invite follow app service implementation
 *
 * @author Levent8421
 */
@Service
public class InviteFollowAppServiceImpl extends AbstractServiceImpl<InviteFollowApp> implements InviteFollowAppService {
    private final InviteFollowAppMapper inviteFollowAppMapper;

    public InviteFollowAppServiceImpl(InviteFollowAppMapper inviteFollowAppMapper) {
        super(inviteFollowAppMapper);
        this.inviteFollowAppMapper = inviteFollowAppMapper;
    }

    @Override
    public List<InviteFollowApp> findByMerchant(Integer merchantId) {
        final InviteFollowApp query = new InviteFollowApp();
        query.setMerchantId(merchantId);
        return findByQuery(query);
    }
}
