package com.levent8421.wechat.tools.commons.utils.encrypt;

/**
 * Create By Levent8421
 * Create Time: 2020/8/26 1:21
 * Class Name: MD5UtilsTest
 * Author: Levent8421
 * Description:
 * MD5 Encrypt test
 *
 * @author Levent8421
 */

public class MD5UtilsTest {
    public void test() {
        final String source = "111111";
        final String salt = "simple";
        final String md5 = MD5Utils.md5(source, salt);
        System.out.println(md5);
        final boolean matched = MD5Utils.isMatched(md5, source);
        System.out.println("matched:" + matched);
    }

    public static void main(String[] args) {
        new MD5UtilsTest().test();
    }
}
