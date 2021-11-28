package com.levent8421.wechat.tools.model.service.general.listener;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;

/**
 * Create by Levent8421
 * Date: 2021/11/27 17:21
 * ClassName: SuperCtlActionCompleteListener
 * Description:
 * Action Complete Lis
 *
 * @author levent8421
 */
@FunctionalInterface
public interface SuperCtlActionCompleteListener {
    /**
     * On Action complete
     *
     * @param action action
     */
    void onComplete(SuperCtlAction action);
}
