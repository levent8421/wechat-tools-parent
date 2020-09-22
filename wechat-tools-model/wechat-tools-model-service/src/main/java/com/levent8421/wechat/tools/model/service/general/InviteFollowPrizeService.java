package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:42
 * Class Name: InviteFollowPrizeService
 * Author: Levent8421
 * Description:
 * 关注邀请APP奖品业务行为定义
 *
 * @author Levent8421
 */
public interface InviteFollowPrizeService extends AbstractService<InviteFollowPrize> {
    /**
     * Find prize by app id
     *
     * @param appId app id
     * @return prizes
     */
    List<InviteFollowPrize> findByApp(Integer appId);

    /**
     * 创建奖品
     *
     * @param prize 奖品信息
     * @return prize
     */
    InviteFollowPrize create(InviteFollowPrize prize);

    /**
     * 更新奖品信息
     *
     * @param prize prize
     * @return 更新结果
     */
    InviteFollowPrize updatePrizeInfo(InviteFollowPrize prize);
}
