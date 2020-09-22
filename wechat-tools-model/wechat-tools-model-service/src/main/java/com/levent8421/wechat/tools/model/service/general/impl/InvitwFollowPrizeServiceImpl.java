package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowPrizeMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowPrizeService;
import org.springframework.stereotype.Service;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:43
 * Class Name: InvitwFollowPrizeServiceImpl
 * Author: Levent8421
 * Description:
 * 关注抽奖应用奖品业务行为实现
 *
 * @author Levent8421
 */
@Service
public class InvitwFollowPrizeServiceImpl extends AbstractServiceImpl<InviteFollowPrize> implements InviteFollowPrizeService {
    private final InviteFollowPrizeMapper inviteFollowPrizeMapper;

    public InvitwFollowPrizeServiceImpl(InviteFollowPrizeMapper inviteFollowPrizeMapper) {
        super(inviteFollowPrizeMapper);
        this.inviteFollowPrizeMapper = inviteFollowPrizeMapper;
    }
}
