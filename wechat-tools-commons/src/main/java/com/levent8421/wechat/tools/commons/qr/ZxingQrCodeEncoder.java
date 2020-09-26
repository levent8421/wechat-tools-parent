package com.levent8421.wechat.tools.commons.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Create By Levent8421
 * Create Time: 2020/9/26 11:21
 * Class Name: ZxingQrCodeEncoder
 * Author: Levent8421
 * Description:
 * QrCode encoder implement with zxing
 *
 * @author Levent8421
 */
public class ZxingQrCodeEncoder extends AbstractQrCodeEncoder {
    private static final int BACKGROUND = Color.WHITE.getRGB();
    private static final int FOREGROUND = Color.BLACK.getRGB();

    @Override
    public BufferedImage encodeAsImage(String content, int width, int height, int margin) {
        final HashMap<EncodeHintType, Object> hints = new HashMap<>(16);
        hints.put(EncodeHintType.MARGIN, margin);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            final BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    content, BarcodeFormat.QR_CODE, width, height, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig(FOREGROUND, BACKGROUND));
        } catch (WriterException e) {
            throw new InternalServerErrorException("Error on convert BitMatrix to BufferedImage", e);
        }
    }
}
