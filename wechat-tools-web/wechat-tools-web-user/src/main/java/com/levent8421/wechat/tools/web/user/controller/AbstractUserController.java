package com.levent8421.wechat.tools.web.user.controller;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;

import java.util.Objects;

/**
 * Create By leven ont 2020/9/24 0:03
 * Class Name :[AbstractUserController]
 * <p>
 * 用户端控制器基类
 *
 * @author leven
 */
public abstract class AbstractUserController extends AbstractController {
    /**
     * 当前用户ID
     *
     * @param tokenDataHolder token data holder
     * @return user id
     */
    protected Integer getUserId(TokenDataHolder tokenDataHolder) {
        return tokenDataHolder.get(UserToken.USER_ID_NAME, Integer.class);
    }

    /**
     * Get current user id (Not null)
     *
     * @param tokenDataHolder data holder
     * @return user id
     */
    protected Integer requireUserId(TokenDataHolder tokenDataHolder) {
        final Integer userId = getUserId(tokenDataHolder);
        if (userId == null) {
            throw new PermissionDeniedException("用户未登录");
        }
        return userId;
    }

    /**
     * Current user info
     *
     * @param tokenDataHolder   data holder
     * @param wechatUserService service
     * @return User
     */
    protected WechatUser getUser(TokenDataHolder tokenDataHolder, WechatUserService wechatUserService) {
        return wechatUserService.get(getUserId(tokenDataHolder));
    }

    /**
     * Current user info (Non null)
     *
     * @param tokenDataHolder   data holder
     * @param wechatUserService service
     * @return user
     */
    protected WechatUser requireUser(TokenDataHolder tokenDataHolder, WechatUserService wechatUserService) {
        return wechatUserService.require(requireUserId(tokenDataHolder));
    }

    /**
     * 当前商户ID
     *
     * @param tokenDataHolder token data holder
     * @return merchant id
     */
    protected Integer getMerchantId(TokenDataHolder tokenDataHolder) {
        return tokenDataHolder.get(UserToken.MERCHANT_ID_NAME, Integer.class);
    }

    /**
     * Require current merchant id
     *
     * @param tokenDataHolder data holder
     * @return merchant id
     */
    protected Integer requireMerchantId(TokenDataHolder tokenDataHolder) {
        final Integer merchantId = getMerchantId(tokenDataHolder);
        if (merchantId == null) {
            throw new PermissionDeniedException("用户未登录");
        }
        return merchantId;
    }

    /**
     * Select current merchant  from database
     *
     * @param tokenDataHolder data holder
     * @param merchantService service component
     * @return merchant
     */
    protected Merchant getMerchant(TokenDataHolder tokenDataHolder, MerchantService merchantService) {
        final Integer merchantId = getMerchantId(tokenDataHolder);
        if (merchantId == null) {
            return null;
        }
        return merchantService.require(merchantId);
    }

    /**
     * Require current merchant from database
     *
     * @param tokenDataHolder data holder
     * @param merchantService service
     * @return merchant
     */
    protected Merchant requireMerchant(TokenDataHolder tokenDataHolder, MerchantService merchantService) {
        return merchantService.require(requireMerchantId(tokenDataHolder));
    }

    /**
     * 检查被操作对象所属的用户ID与当前登录的用户ID是否一致
     *
     * @param tokenDataHolder data holder
     * @param userId          userId
     */
    protected void checkPermissionByUser(TokenDataHolder tokenDataHolder, Integer userId) {
        final Integer tokenUserId = requireUserId(tokenDataHolder);
        if (!Objects.equals(tokenUserId, userId)) {
            throw new BadRequestException("您无权操作该对象");
        }
    }

    /**
     * 检查被操作对象所属的商户ID与当前登录的用户的商户ID是否一致
     *
     * @param tokenDataHolder data holder
     * @param merchantId      merchantId
     */
    protected void checkPermissionByMerchant(TokenDataHolder tokenDataHolder, Integer merchantId) {
        final Integer tokenMerchantId = requireMerchantId(tokenDataHolder);
        if (!Objects.equals(tokenMerchantId, merchantId)) {
            throw new BadRequestException("您无权操作该对象");
        }
    }
}
