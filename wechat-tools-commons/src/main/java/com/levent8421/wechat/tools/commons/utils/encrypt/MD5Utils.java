package com.levent8421.wechat.tools.commons.utils.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 0:11
 * Class Name: MD5Utils
 * Author: Levent8421
 * Description:
 * MD5工具类
 *
 * @author Levent8421
 */
public class MD5Utils {
    private static final String SALT_DELIMITER = ",";
    private static final int ARRAY_SIZE_SALT_AND_MD5 = 2;


    /**
     * Dont instance this class
     */
    private MD5Utils() {
    }

    /**
     * MD5 签名
     *
     * @param source 被签名字符串
     * @return 签名结果
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }

    /**
     * 带盐的MD5签名算法
     *
     * @param source 被签名字符串
     * @param salt   salt
     * @return 签名结果
     */
    public static String md5(String source, String salt) {
        final String result = md5(source + salt);
        return String.format("%s%s%s", result, SALT_DELIMITER, salt);
    }

    public static boolean isMatched(String encrypted, String source) {
        if (StringUtils.isBlank(encrypted) || StringUtils.isBlank(source)) {
            return false;
        }
        if (!encrypted.contains(SALT_DELIMITER)) {
            return false;
        }
        final String[] pair = encrypted.split(SALT_DELIMITER);
        if (pair.length != ARRAY_SIZE_SALT_AND_MD5) {
            return false;
        }
        final String salt = pair[1];
        final String md5Result = md5(source, salt);
        return Objects.equals(md5Result, encrypted);
    }
}
