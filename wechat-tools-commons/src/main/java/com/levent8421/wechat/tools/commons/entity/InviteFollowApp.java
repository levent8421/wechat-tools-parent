package com.levent8421.wechat.tools.commons.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.levent8421.wechat.tools.commons.context.Datetime;
import com.levent8421.wechat.tools.commons.dto.LinkImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 10:20
 * Class Name: InviteFollowApp
 * Author: Levent8421
 * Description:
 * 转发关注应用实体类
 *
 * @author Levent8421
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_invite_follow_app")
public class InviteFollowApp extends AbstractEntity {
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
    /**
     * 商户ID
     */
    @Column(name = "merchant_id", length = 10, nullable = false)
    private Integer merchantId;
    /**
     * 主题颜色
     */
    @Column(name = "theme_color", nullable = false, length = 100)
    private String themeColor;
    /**
     * 顶部横幅图片
     */
    @Column(name = "banner_image")
    private String bannerImage;
    /**
     * 标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 副标题
     */
    @Column(name = "subtitle")
    private String subtitle;
    /**
     * 底部文字
     */
    @Column(name = "footer_text")
    private String footerText;
    /**
     * 抽奖按钮图片
     */
    @Column(name = "button_image")
    private String buttonImage;
    /**
     * 规则说明文字
     */
    @Column(name = "rules_text")
    private String rulesText;
    /**
     * 截止日期
     */
    @Column(name = "deadline")
    @JSONField(format = Datetime.DATE_FORMAT)
    @DateTimeFormat(pattern = Datetime.DATE_FORMAT)
    @JsonFormat(pattern = Datetime.DATE_FORMAT)
    private Date deadline;
    /**
     * 是否需要输入电话号码
     */
    @Column(name = "phone_required", nullable = false)
    private Boolean phoneRequired;
    /**
     * 图片列表
     */
    @Column(name = "images_json")
    private String imagesJson;
    /**
     * 图片列表
     */
    private List<LinkImage> images;
    /**
     * APP状态
     */
    @Column(name = "state", nullable = false, length = 3)
    private Integer state;
    /**
     * 是否为默认应用
     */
    @Column(name = "default_app", nullable = false)
    private Boolean defaultApp;
}
