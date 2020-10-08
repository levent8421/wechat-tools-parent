package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.InviteFollowDraw;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create By Levent8421
 * Create Time: 2020/10/8 22:51
 * Class Name: InviteFollowDrawService
 * Author: Levent8421
 * Description:
 * 抽奖记录相关业务组件行为定义
 *
 * @author Levent8421
 */
public interface InviteFollowDrawService extends AbstractService<InviteFollowDraw> {
    /**
     * 查询指定用户在指定APP下发生测抽奖次数
     *
     * @param userId 用户ID
     * @param appId  APP_ID
     * @return count
     */
    int countByUserAndApp(Integer userId, Integer appId);
}
