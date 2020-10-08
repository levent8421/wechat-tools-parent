package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.InviteFollowLog;
import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

/**
 * Create By leven ont 2020/9/13 20:35
 * Class Name :[InviteFollowLogService]
 * <p>
 * 邀请关注记录业务行为定义
 *
 * @author leven
 */
public interface InviteFollowLogService extends AbstractService<InviteFollowLog> {
    /**
     * 记录用户抽奖操作
     *
     * @param user    用户
     * @param inviter 邀请人
     * @param app     抽奖应用
     * @param prize   中奖奖品
     * @return log
     */
    InviteFollowLog log(WechatUser user,
                        WechatUser inviter,
                        InviteFollowApp app,
                        InviteFollowPrize prize);
}
