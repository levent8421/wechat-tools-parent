package com.levent8421.wechat.tools.commons.qr;

import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create By Levent8421
 * Create Time: 2020/9/26 11:22
 * Class Name: AbstractQrCodeEncoder
 * Author: Levent8421
 * Description:
 * QrCode编码器基类
 *
 * @author Levent8421
 */
public abstract class AbstractQrCodeEncoder implements QrCodeEncoder {
    private static final String IMAGE_FORMAT = "png";

    @Override
    public void encodeToStream(String content, int width, int height, int margin, OutputStream stream) {
        final BufferedImage image = encodeAsImage(content, width, height, margin);
        try {
            ImageIO.write(image, IMAGE_FORMAT, stream);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on write image into stream!", e);
        }
    }

    @Override
    public byte[] encodeAsBytes(String content, int width, int height, int margin) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            encodeToStream(content, width, height, margin, out);
            out.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on convert stream to bytes!", e);
        }
    }
}
