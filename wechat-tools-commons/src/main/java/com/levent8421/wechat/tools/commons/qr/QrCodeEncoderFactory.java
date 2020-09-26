package com.levent8421.wechat.tools.commons.qr;

import java.util.HashMap;

/**
 * Create By Levent8421
 * Create Time: 2020/9/26 11:31
 * Class Name: QrCodeEncoderFactory
 * Author: Levent8421
 * Description:
 * 二维码编码器工厂类
 *
 * @author Levent8421
 */
public class QrCodeEncoderFactory extends HashMap<Integer, QrCodeEncoder> {
    public static final QrCodeEncoderFactory INSTANCE = new QrCodeEncoderFactory();

    private QrCodeEncoderFactory() {
        put(ZXING, new ZxingQrCodeEncoder());
    }

    public static final int ZXING = 0x01;

    public static QrCodeEncoder getEncoder(int type) {
        return INSTANCE.requireEncoder(type);
    }

    public QrCodeEncoder requireEncoder(int type) {
        if (!containsKey(type)) {
            throw new IllegalArgumentException("Can not find encoder by type " + type);
        }
        return get(type);
    }
}
