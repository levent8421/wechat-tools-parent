package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.InviteFollowLog;
import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowLogMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Create By leven ont 2020/9/13 20:36
 * Class Name :[InviteFollowLogServiceImpl]
 * <p>
 * 邀请关注业务行为实现
 *
 * @author leven
 */
@Service
@Slf4j
public class InviteFollowLogServiceImpl extends AbstractServiceImpl<InviteFollowLog> implements InviteFollowLogService {
    private final InviteFollowLogMapper inviteFollowLogMapper;

    public InviteFollowLogServiceImpl(InviteFollowLogMapper inviteFollowLogMapper) {
        super(inviteFollowLogMapper);
        this.inviteFollowLogMapper = inviteFollowLogMapper;
    }

    @Override
    public InviteFollowLog log(WechatUser user,
                               WechatUser inviter,
                               InviteFollowApp app,
                               InviteFollowPrize prize) {
        final InviteFollowLog inviteFollowLog = new InviteFollowLog();
        inviteFollowLog.setUserId(user.getId());
        inviteFollowLog.setInviterId(inviter.getId());
        inviteFollowLog.setMerchantId(app.getMerchantId());
        inviteFollowLog.setAppId(app.getId());
        inviteFollowLog.setStayFocused(true);
        if (prize != null) {
            inviteFollowLog.setPrizeId(prize.getId());
        }
        return save(inviteFollowLog);
    }
}
