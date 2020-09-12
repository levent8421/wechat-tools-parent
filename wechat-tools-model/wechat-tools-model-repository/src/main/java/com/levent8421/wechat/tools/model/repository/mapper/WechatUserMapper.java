package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 11:11
 * Class Name: WechatUserMapper
 * Author: Levent8421
 * Description:
 * Wechat User Mapper
 *
 * @author Levent8421
 */
@Repository
public interface WechatUserMapper extends AbstractMapper<WechatUser> {
    /**
     * Select User by openId
     *
     * @param merchantId 商户ID
     * @param openId     openId
     * @return user
     */
    WechatUser selectByOpenId(@Param("merchantId") Integer merchantId, @Param("openId") String openId);

    /**
     * Select User By UnionId
     *
     * @param merchantId 商户ID
     * @param unionId    unionId
     * @return User
     */
    WechatUser selectByUnionId(@Param("merchantId") Integer merchantId, @Param("unionId") String unionId);
}
