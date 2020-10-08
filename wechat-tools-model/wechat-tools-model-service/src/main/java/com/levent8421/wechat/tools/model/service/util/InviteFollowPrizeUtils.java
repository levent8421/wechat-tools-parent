package com.levent8421.wechat.tools.model.service.util;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import org.apache.commons.lang3.RandomUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Create By Levent8421
 * Create Time: 2020/10/5 21:00
 * Class Name: InviteFollowPrizeUtils
 * Author: Levent8421
 * Description:
 * 关注邀请APP奖品相关工具类
 *
 * @author Levent8421
 */
public class InviteFollowPrizeUtils {
    private static final int RANDOM_NUM_START = 0;
    /**
     * 最低中奖率千万分之一
     */
    private static final int RANDOM_NUM_END = 100 * 100000;

    /**
     * 抽奖算法
     *
     * @param prizes 奖品列表
     * @return 中奖奖品
     */
    public static InviteFollowPrize draw(Collection<InviteFollowPrize> prizes) {
        final List<InviteFollowPrize> availablePrizes = prizes.stream()
                .filter(InviteFollowPrizeUtils::isAvailable)
                .filter(InviteFollowPrizeUtils::hasMoreStock)
                .collect(Collectors.toList());
        return doDraw(availablePrizes);
    }

    /**
     * 执行抽奖逻辑
     *
     * @param prizes 奖品列表
     * @return 中奖奖品
     */
    private static InviteFollowPrize doDraw(Collection<InviteFollowPrize> prizes) {
        final int random = RandomUtils.nextInt(RANDOM_NUM_START, RANDOM_NUM_END);
        int sum = 0;
        for (InviteFollowPrize prize : prizes) {
            sum += prize.getWinningRate();
            if (random <= sum) {
                return prize;
            }
        }
        return null;
    }

    /**
     * 检查奖品是否可用
     *
     * @param prize 奖品
     * @return available
     */
    public static boolean isAvailable(InviteFollowPrize prize) {
        return prize != null && Objects.equals(prize.getState(), InviteFollowPrize.STATE_AVAILABLE);
    }

    /**
     * 检查奖品库存
     *
     * @param prize 奖品
     * @return true: 仍有库存 false: 库存不足
     */
    public static boolean hasMoreStock(InviteFollowPrize prize) {
        return prize != null
                && prize.getTotalStock() != null
                && prize.getTotalStock() > 0;
    }
}
