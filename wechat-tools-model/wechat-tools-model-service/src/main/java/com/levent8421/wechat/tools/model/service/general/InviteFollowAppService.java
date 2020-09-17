package com.levent8421.wechat.tools.model.service.general;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:04
 * Class Name: InviteFollowAppService
 * Author: Levent8421
 * Description:
 * Invite follow app service
 *
 * @author Levent8421
 */
public interface InviteFollowAppService extends AbstractService<InviteFollowApp> {
    /**
     * Find invite follow app by merchant id
     *
     * @param merchantId merchant id
     * @return invite follow apps
     */
    List<InviteFollowApp> findByMerchant(Integer merchantId);
}
