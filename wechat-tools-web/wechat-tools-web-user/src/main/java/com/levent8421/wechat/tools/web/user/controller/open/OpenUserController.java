package com.levent8421.wechat.tools.web.user.controller.open;

import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.profile.ProfileHelpher;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;
import com.levent8421.wechat.tools.web.user.vo.UserTokenVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:26
 * Class Name: OpenUserController
 * Author: Levent8421
 * Description:
 * Open User Controller
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/open/user")
public class OpenUserController extends AbstractApiController {
    private final TokenConfiguration tokenConfiguration;
    private final WechatUserService wechatUserService;
    private final ProfileHelpher profileHelpher;

    public OpenUserController(TokenConfiguration tokenConfiguration,
                              WechatUserService wechatUserService,
                              ProfileHelpher profileHelpher) {
        this.tokenConfiguration = tokenConfiguration;
        this.wechatUserService = wechatUserService;
        this.profileHelpher = profileHelpher;
    }

    /**
     * 模拟登录
     *
     * @param id user id
     * @return GR
     */
    @GetMapping("/{id}/_mock-login")
    public GeneralResult<UserTokenVo> mockLogin(@PathVariable("id") Integer id) {
        if (!profileHelpher.isDev()) {
            throw new BadRequestException("该接口仅允许开发环境使用");
        }
        final WechatUser user = wechatUserService.require(id);
        final String token = encodeTokenString(user);
        final UserTokenVo res = new UserTokenVo();
        res.setToken(token);
        res.setWechatUser(user);
        return GeneralResult.ok(res);
    }

    private String encodeTokenString(WechatUser wechatUser) {
        return new UserToken(wechatUser, tokenConfiguration.getKey()).toTokenString();
    }
}
