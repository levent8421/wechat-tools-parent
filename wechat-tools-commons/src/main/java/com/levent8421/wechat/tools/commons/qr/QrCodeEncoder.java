package com.levent8421.wechat.tools.commons.qr;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * Create By Levent8421
 * Create Time: 2020/9/26 10:07
 * Class Name: QrCodeEncoder
 * Author: Levent8421
 * Description:
 * 二维码编码器
 *
 * @author Levent8421
 */
public interface QrCodeEncoder {
    /**
     * 生成二维码为BufferImage对象
     *
     * @param content 二维码内容
     * @param width   宽度
     * @param height  高度
     * @param margin  边距
     * @return image
     */
    BufferedImage encodeAsImage(String content, int width, int height, int margin);

    /**
     * 生成二维码并写入输出流中
     *
     * @param content 二维码内容
     * @param width   宽度
     * @param height  高度
     * @param margin  边距
     * @param stream  输出流
     */
    void encodeToStream(String content, int width, int height, int margin, OutputStream stream);

    /**
     * 生成二维码为二进制数组
     *
     * @param content 二维码内容
     * @param width   宽度
     * @param height  高度
     * @param margin  边距
     * @return bytes
     */
    byte[] encodeAsBytes(String content, int width, int height, int margin);
}
