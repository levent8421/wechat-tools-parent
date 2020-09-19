package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.InviteFollowLog;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create By Levent8421
 * Create Time: 2020/9/13 16:05
 * Class Name: InviteFollowLogMapper
 * Author: Levent8421
 * Description:
 * 邀请关注记录相关数据库访问组件
 *
 * @author Levent8421
 */
@Repository
public interface InviteFollowLogMapper extends AbstractMapper<InviteFollowLog> {

}
