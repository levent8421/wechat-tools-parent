package com.levent8421.wechat.tools.web.merchant.controller.open;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.vo.TokenAccountVo;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

/**
 * Create By Levent8421
 * Create Time: 2020/9/9 19:06
 * Class Name: OpenMerchantController
 * Author: Levent8421
 * Description:
 * 开发的 商户信息数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/open/merchant")
public class OpenMerchantController extends AbstractController {
    private final MerchantService merchantService;
    private final TokenConfiguration tokenConfiguration;

    public OpenMerchantController(MerchantService merchantService, TokenConfiguration tokenConfiguration) {
        this.merchantService = merchantService;
        this.tokenConfiguration = tokenConfiguration;
    }

    /**
     * 商户登录
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/_login")
    public GeneralResult<TokenAccountVo<Merchant>> login(@RequestBody Merchant param) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getLoginName(), error, "请输入登录名");
        notEmpty(param.getPassword(), error, "请输入密码");

        final String loginName = param.getLoginName();
        final String password = param.getPassword();
        final Merchant merchant = merchantService.login(loginName, password);
        final String token = buildMerchantToken(merchant);
        final TokenAccountVo<Merchant> res = new TokenAccountVo<>();
        res.setAccount(merchant);
        res.setToken(token);
        return GeneralResult.ok(res);
    }

    private String buildMerchantToken(Merchant merchant) {
        final MerchantToken token = new MerchantToken(tokenConfiguration.getKey(), merchant);
        return token.toTokenString();
    }
}
