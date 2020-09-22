package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowPrizeMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowPrizeService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    private static final String DEFAULT_IMAGE = "default.png";
    private final InviteFollowPrizeMapper inviteFollowPrizeMapper;

    public InvitwFollowPrizeServiceImpl(InviteFollowPrizeMapper inviteFollowPrizeMapper) {
        super(inviteFollowPrizeMapper);
        this.inviteFollowPrizeMapper = inviteFollowPrizeMapper;
    }

    @Override
    public List<InviteFollowPrize> findByApp(Integer appId) {
        final InviteFollowPrize query = new InviteFollowPrize();
        query.setInviteFollowAppId(appId);
        return findByQuery(query);
    }

    @Override
    public InviteFollowPrize create(InviteFollowPrize prize) {
        Integer totalWinningRate = inviteFollowPrizeMapper.selectSumWinningRateByApp(prize.getInviteFollowAppId());
        totalWinningRate = totalWinningRate == null ? 0 : totalWinningRate;
        if (totalWinningRate + prize.getWinningRate() > InviteFollowPrize.MAX_WINNING_RATE) {
            throw new BadRequestException("中奖率总和不能超过[" + InviteFollowPrize.MAX_WINNING_RATE + "]!");
        }
        prize.setSales(0);
        prize.setState(InviteFollowPrize.STATE_AVAILABLE);
        prize.setImage(DEFAULT_IMAGE);
        return save(prize);
    }

    @Override
    public InviteFollowPrize updatePrizeInfo(InviteFollowPrize prize) {
        Integer totalWinningRate = inviteFollowPrizeMapper.selectSumWinningRateByAppWithExclude(
                prize.getInviteFollowAppId(), Collections.singletonList(prize.getId()));
        totalWinningRate = totalWinningRate == null ? 0 : totalWinningRate;
        if (totalWinningRate + prize.getWinningRate() > InviteFollowPrize.MAX_WINNING_RATE) {
            throw new BadRequestException("中奖率总和不能超过[" + InviteFollowPrize.MAX_WINNING_RATE + "]!");
        }
        return updateById(prize);
    }
}
