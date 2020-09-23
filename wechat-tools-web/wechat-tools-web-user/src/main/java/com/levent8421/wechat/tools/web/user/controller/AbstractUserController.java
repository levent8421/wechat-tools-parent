package com.levent8421.wechat.tools.web.user.controller;

import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;

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
}
