package com.levent8421.wechat.tools.model.repository.mapper;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/21 22:38
 * Class Name: InviteFollowPrizeMapper
 * Author: Levent8421
 * Description:
 * 关注抽奖应应用奖品
 *
 * @author Levent8421
 */
@Repository
public interface InviteFollowPrizeMapper extends AbstractMapper<InviteFollowPrize> {
    /**
     * 查询某APP的奖品总中奖率
     *
     * @param appId appId
     * @return winningRate sum
     */
    Integer selectSumWinningRateByApp(@Param("appId") Integer appId);

    /**
     * 查询某APP的奖品总中奖率，并排除指定奖品
     *
     * @param appId           appId
     * @param excludePrizeIds 排除的奖品ID
     * @return winning rate sum
     */
    Integer selectSumWinningRateByAppWithExclude(@Param("appId") Integer appId,
                                                 @Param("excludePrizeIds") List<Integer> excludePrizeIds);

    /**
     * 查询科用的奖品（by appId）
     *
     * @param appId appId
     * @return prizes
     */
    List<InviteFollowPrize> selectAvailableByAppId(@Param("appId") Integer appId);

    /**
     * 减库存
     *
     * @param id prize id
     * @param ct 数量
     * @return rows
     */
    Integer countDownStock(@Param("id") Integer id, @Param("ct") Integer ct);
}
