package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowLog;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowLogMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowLogService;
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
public class InviteFollowLogServiceImpl extends AbstractServiceImpl<InviteFollowLog> implements InviteFollowLogService {
    private final InviteFollowLogMapper inviteFollowLogMapper;

    public InviteFollowLogServiceImpl(InviteFollowLogMapper inviteFollowLogMapper) {
        super(inviteFollowLogMapper);
        this.inviteFollowLogMapper = inviteFollowLogMapper;
    }
}
