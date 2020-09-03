package com.levent8421.wechat.tools.web.commons.vo;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/8/28 22:04
 * Class Name: PaginationParam
 * Author: Levent8421
 * Description:
 * 分页参数
 *
 * @author Levent8421
 */
@Data
public class PaginationParam {
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 每页显示数据数量
     */
    private int rows = 20;
}
