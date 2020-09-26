package com.levent8421.wechat.tools.web.commons.vo;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/9/26 16:49
 * Class Name: QrCodeInfo
 * Author: Levent8421
 * Description:
 * 二维码信息
 *
 * @author Levent8421
 */
@Data
public class QrCodeInfo {
    private String imageUrl;
    private String content;
}
