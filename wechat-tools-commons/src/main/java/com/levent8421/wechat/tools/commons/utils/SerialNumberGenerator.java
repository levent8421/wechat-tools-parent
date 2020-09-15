package com.levent8421.wechat.tools.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Create By Levent8421
 * Create Time: 2020/8/29 0:49
 * Class Name: SerialNumberGenerator
 * Author: Levent8421
 * Description:
 * 序列号生成器
 *
 * @author Levent8421
 */
public class SerialNumberGenerator {
    private final NumberFormat numberFormat;
    private final AtomicLong numberGenerator = new AtomicLong();
    private final String prefix;
    private final String suffix;
    private final int maxNumber;

    public SerialNumberGenerator(String prefix, String suffix, int maxNumberLen) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.maxNumber = (int) (Math.pow(10, maxNumberLen) - 1);
        this.numberFormat = buildNumberFormat(maxNumberLen);
    }

    private NumberFormat buildNumberFormat(int len) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append('0');
        }
        return new DecimalFormat(sb.toString());
    }

    private String currentTimeString() {
        final String timeStr = String.valueOf(System.currentTimeMillis());
        return StringUtils.reverse(timeStr);
    }

    private synchronized String nextNumber() {
        final long number = numberGenerator.getAndIncrement();
        if (number >= maxNumber) {
            numberGenerator.set(0);
        }
        return numberFormat.format(number);
    }

    public String next() {
        final String time = currentTimeString();
        final String number = nextNumber();
        return String.format("%s%s%s%s", prefix, time, number, suffix);
    }
}
