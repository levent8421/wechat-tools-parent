package com.levent8421.wechat.tools.web.merchant.controller;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;

import java.util.Objects;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:11
 * Class Name: AbstractMerchantController
 * Author: Levent8421
 * Description:
 * 商户应用控制器基类
 *
 * @author Levent8421
 */
public abstract class AbstractMerchantController extends AbstractController {
    /**
     * 从token数据保持器中获取当前商户的ID
     *
     * @param tokenDataHolder token data holder
     * @return merchant id
     */
    protected Integer getCurrentMerchantId(TokenDataHolder tokenDataHolder) {
        return tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
    }

    /**
     * require merchant id
     *
     * @param tokenDataHolder token data holder
     * @return merchant id
     */
    protected Integer requireCurrentMerchantId(TokenDataHolder tokenDataHolder) {
        final Integer id = getCurrentMerchantId(tokenDataHolder);
        if (id == null) {
            throw new PermissionDeniedException("商户未登录");
        }
        return id;
    }

    /**
     * 从数据库中查找当前商户的信息
     *
     * @param merchantService 商户业务组件
     * @param tokenDataHolder token data holder
     * @return merchant
     */
    protected Merchant getCurrentMerchant(MerchantService merchantService, TokenDataHolder tokenDataHolder) {
        final Integer currentMerchantId = getCurrentMerchantId(tokenDataHolder);
        if (currentMerchantId == null) {
            return null;
        }
        return merchantService.get(currentMerchantId);
    }

    /**
     * 从数据库中查找商户信息
     *
     * @param merchantService 商户业务组件
     * @param tokenDataHolder token data holder
     * @return merchant
     */
    protected Merchant requireCurrentMerchant(MerchantService merchantService, TokenDataHolder tokenDataHolder) {
        final Integer currentMerchantId = requireCurrentMerchantId(tokenDataHolder);
        return merchantService.require(currentMerchantId);
    }

    /**
     * 检查操作对象的商户ID与当前登录的商户ID是否一致，不一致时抛出异常
     *
     * @param tokenDataHolder token数据保持器
     * @param merchantId      操作对象商户ID
     */
    protected void checkPermission(TokenDataHolder tokenDataHolder, Integer merchantId) {
        final Integer myId = requireCurrentMerchantId(tokenDataHolder);
        if (!Objects.equals(merchantId, myId)) {
            throw new BadRequestException("您无权操作该应用！");
        }
    }
}
