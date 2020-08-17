package com.levent8421.wechat.tools.commons.utils.fn;

/**
 * Create by 郭文梁 2019/6/12 0012 16:32
 * Function
 * 一个参数 一个返回值的函数
 *
 * @author 郭文梁
 * @data 2019/6/12 0012
 */
@FunctionalInterface
public interface Function<T, R> {
    /**
     * 执行
     *
     * @param arg 参数
     * @return 返回值
     */
    R exec(T arg);
}
