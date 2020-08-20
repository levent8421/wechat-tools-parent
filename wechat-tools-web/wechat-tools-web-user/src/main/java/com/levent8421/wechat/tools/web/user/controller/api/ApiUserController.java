package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/20 18:48
 * Class Name: ApiUserController
 * Author: Levent8421
 * Description:
 * Api User Controller
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/user")
@Slf4j
public class ApiUserController extends AbstractApiController {
    private final TokenDataHolder tokenDataHolder;

    public ApiUserController(TokenDataHolder tokenDataHolder) {
        this.tokenDataHolder = tokenDataHolder;
    }

    @GetMapping("/_test-token")
    public GeneralResult<?> testToken() {
        final Integer merchantId = tokenDataHolder.get(UserToken.MERCHANT_ID_NAME, Integer.class, null);
        final Integer userId = tokenDataHolder.get(UserToken.USER_ID_NAME, Integer.class, null);
        log.info("token =[{},{}]", userId, merchantId);
        return GeneralResult.ok();
    }
}
