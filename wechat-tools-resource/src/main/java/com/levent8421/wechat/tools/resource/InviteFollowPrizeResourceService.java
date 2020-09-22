package com.levent8421.wechat.tools.resource;

import com.levent8421.wechat.tools.commons.entity.InviteFollowPrize;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create By Levent8421
 * Create Time: 2020/9/22 18:33
 * Class Name: InviteFollowPrizeResourceService
 * Author: Levent8421
 * Description:
 * 关注抽奖应用资源业务行为定义
 *
 * @author Levent8421
 */
public interface InviteFollowPrizeResourceService extends EntityResourceService<InviteFollowPrize> {
    /**
     * 保存图片
     *
     * @param file 图片文件
     * @return 文件名称
     */
    String saveImage(MultipartFile file);
}
