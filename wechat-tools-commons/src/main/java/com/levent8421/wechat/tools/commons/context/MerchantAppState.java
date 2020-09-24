package com.levent8421.wechat.tools.commons.context;

/**
 * Create By leven ont 2020/9/24 22:20
 * Class Name :[MerchantAppState]
 * <p>
 * 商户APP状态常量表
 *
 * @author leven
 */
public class MerchantAppState {
    /**
     * 状态： 刚创建 等待审核
     */
    public static final int STATE_INIT = 0x01;
    /**
     * 状态： 审核通过 允许上线
     */
    public static final int STATE_AVAILABLE = 0x02;
    /**
     * 状态： 审核未通过
     */
    public static final int STATE_REFUSE = 0x03;
    /**
     * 状态： 活动已结束
     */
    public static final int STATE_FINISHED = 0x04;
}
