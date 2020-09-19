package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 10:51
 * Class Name: InviteFollowAppMapper
 * Author: Levent8421
 * Description:
 * 转发邀请关注app相关数据库访问组件
 * base on mybatis
 *
 * @author Levent8421
 */
@Repository
public interface InviteFollowAppMapper extends AbstractMapper<InviteFollowApp> {
    /**
     * 通过商户更新app默认标志
     *
     * @param merchantId 商户ID
     * @param defaultApp 默认标志
     * @return rows
     */
    int updateDefaultAppByMerchant(@Param("merchantId") Integer merchantId,
                                   @Param("defaultApp") boolean defaultApp);
}
