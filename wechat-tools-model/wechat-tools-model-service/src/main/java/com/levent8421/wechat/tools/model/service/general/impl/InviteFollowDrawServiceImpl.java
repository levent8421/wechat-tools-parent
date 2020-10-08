package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowDraw;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowDrawMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowDrawService;
import org.springframework.stereotype.Service;

/**
 * Create By Levent8421
 * Create Time: 2020/10/8 22:52
 * Class Name: InviteFollowDrawServiceImpl
 * Author: Levent8421
 * Description:
 * Invite Follow Draw Service Implementation
 *
 * @author Levent8421
 */
@Service
public class InviteFollowDrawServiceImpl extends AbstractServiceImpl<InviteFollowDraw> implements InviteFollowDrawService {
    private final InviteFollowDrawMapper inviteFollowDrawMapper;

    public InviteFollowDrawServiceImpl(InviteFollowDrawMapper inviteFollowDrawMapper) {
        super(inviteFollowDrawMapper);
        this.inviteFollowDrawMapper = inviteFollowDrawMapper;
    }

    @Override
    public int countByUserAndApp(Integer userId, Integer appId) {
        final InviteFollowDraw query = new InviteFollowDraw();
        query.setInviteFollowAppId(appId);
        query.setWechatUserId(userId);
        return count(query);
    }
}
