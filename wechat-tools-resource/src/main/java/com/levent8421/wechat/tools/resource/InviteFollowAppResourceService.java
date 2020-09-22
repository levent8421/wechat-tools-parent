package com.levent8421.wechat.tools.resource;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.dto.LinkImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Create By leven ont 2020/9/21 2:01
 * Class Name :[InviteFollowAppResourceService]
 * <p>
 * 关注邀请APP资源业务组件
 *
 * @author leven
 */
public interface InviteFollowAppResourceService extends EntityResourceService<InviteFollowApp> {
    /**
     * 保存顶部图片
     *
     * @param bannerImageFile image file
     * @return file name
     */
    String saveBannerImage(MultipartFile bannerImageFile);

    /**
     * 设置抽奖按钮图片
     *
     * @param buttonImageFile image file
     * @return filename
     */
    String saveButtonImage(MultipartFile buttonImageFile);

    /**
     * 上传新图片
     *
     * @param file      文件
     * @param imageJson merchant.imageJson
     * @param url       链接
     */
    List<LinkImage> appendImage(String imageJson, MultipartFile file, String url);

    /**
     * 删除图片
     *
     * @param index     编号
     * @param imageJson image json array
     */
    List<LinkImage> removeImage(String imageJson, int index);
}
