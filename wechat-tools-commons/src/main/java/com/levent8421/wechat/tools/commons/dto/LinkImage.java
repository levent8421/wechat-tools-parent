package com.levent8421.wechat.tools.commons.dto;

import lombok.Data;

/**
 * Create By leven ont 2020/9/21 23:56
 * Class Name :[LinkImage]
 * <p>
 * 图片链接
 *
 * @author leven
 */
@Data
public class LinkImage {
    public static LinkImage of(String image, String url) {
        final LinkImage res = new LinkImage();
        res.setImage(image);
        res.setUrl(url);
        return res;
    }

    /**
     * 链接
     */
    private String url;
    /**
     * 图片文件名称
     */
    private String image;
}
