package com.levent8421.wechat.tools.web.user.controller.open;

import com.levent8421.wechat.tools.commons.entity.User;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
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

    public OpenUserController(TokenConfiguration tokenConfiguration) {
        this.tokenConfiguration = tokenConfiguration;
    }

    @GetMapping("/_test-token")
    public GeneralResult<String> testToken() {
        final User user = new User();
        user.setMerchantId(1);
        user.setId(1);
        final UserToken token = new UserToken(user, tokenConfiguration.getKey(), tokenConfiguration.getTtl());
        final String tokenString = token.toTokenString();
        return GeneralResult.ok(tokenString);
    }
}
