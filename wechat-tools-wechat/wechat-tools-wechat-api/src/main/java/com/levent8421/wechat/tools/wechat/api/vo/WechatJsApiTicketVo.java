package com.levent8421.wechat.tools.wechat.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Create by 郭文梁 2019/5/18 0018 14:28
 * WechatJsApiTicketVo
 * 微信JsApiTicket
 *
 * @author 郭文梁
 */
@Data
public class WechatJsApiTicketVo {
    @JSONField(name = "errcode")
    private Integer errCode;
    @JSONField(name = "errmsg")
    private String errMsg;
    private String ticket;
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    /**
     * 最后一次刷新时间
     */
    private long refreshTime;

    /**
     * 判断当前Token是否已过期
     *
     * @return 是否已过期
     */
    public boolean expired() {
        if (expiresIn == null) {
            return true;
        }
        long now = System.currentTimeMillis() / 1000;
        return now - refreshTime > expiresIn;
    }
}
