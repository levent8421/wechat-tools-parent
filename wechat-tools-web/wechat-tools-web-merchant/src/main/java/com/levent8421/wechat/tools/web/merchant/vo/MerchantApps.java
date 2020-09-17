package com.levent8421.wechat.tools.web.merchant.vo;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import lombok.Data;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:08
 * Class Name: MerchantApps
 * Author: Levent8421
 * Description:
 * 商户APP view objects
 *
 * @author Levent8421
 */
@Data
public class MerchantApps {
    /**
     * 转发关注抽奖应用
     */
    private List<InviteFollowApp> inviteFollowApps;
}
