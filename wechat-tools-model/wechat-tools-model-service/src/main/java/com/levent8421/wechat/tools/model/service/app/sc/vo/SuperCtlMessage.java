package com.levent8421.wechat.tools.model.service.app.sc.vo;

import lombok.Data;

/**
 * Create by Levent8421
 * Date: 2021/11/25 17:57
 * ClassName: SuperCtlMessage
 * Description:
 * Message
 *
 * @author levent8421
 */
@Data
public class SuperCtlMessage<T> {
    private int id;
    private String action;
    private T payload;
}
