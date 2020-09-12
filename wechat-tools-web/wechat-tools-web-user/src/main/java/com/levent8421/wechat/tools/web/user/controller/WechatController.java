package com.levent8421.wechat.tools.web.user.controller;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.model.service.wechat.WechatResourceException;
import com.levent8421.wechat.tools.model.service.wechat.WechatResourceGenerator;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;
import com.levent8421.wechat.tools.web.user.view.ViewNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By leven ont 2020/9/10 22:07
 * Class Name :[WechatController]
 * <p>
 * 微信相相关数据访问控制器
 *
 * @author leven
 */
@Slf4j
@RestController
@RequestMapping("/api/w")
public class WechatController extends AbstractController {
    private static final String WECHAT_AUTH_STATE = "levent8421";
    private final MerchantService merchantService;
    private final WechatResourceGenerator wechatResourceGenerator;
    private final ViewNameGenerator viewNameGenerator;
    private final TokenConfiguration tokenConfiguration;
    private final WechatUserService wechatUserService;

    public WechatController(MerchantService merchantService,
                            WechatResourceGenerator wechatResourceGenerator,
                            ViewNameGenerator viewNameGenerator,
                            TokenConfiguration tokenConfiguration,
                            WechatUserService wechatUserService) {
        this.merchantService = merchantService;
        this.wechatResourceGenerator = wechatResourceGenerator;
        this.viewNameGenerator = viewNameGenerator;
        this.tokenConfiguration = tokenConfiguration;
        this.wechatUserService = wechatUserService;
    }

    /**
     * 跳转到微信授权链接
     *
     * @param sn Merchant SerialNumber
     * @return ModelAndView
     */
    @GetMapping("/to-auth/{merchantSn}")
    public ModelAndView toWechatAuth(@PathVariable("merchantSn") String sn) {
        final Merchant merchant = merchantService.findBySn(sn);
        String viewName;
        if (merchant == null) {
            viewName = viewNameGenerator.errorPage("商户不存在");
        } else {
            try {
                viewName = wechatResourceGenerator.generateWechatAuthUrl(merchant, WECHAT_AUTH_STATE);
            } catch (WechatResourceException e) {
                log.error("Error on generate wechat auth uri!", e);
                viewName = viewNameGenerator.errorPage(e.getMessage());
            }
        }
        final String redirectViewName = redirect(viewName);
        log.info("Wechat auth [{}]", redirectViewName);
        return new ModelAndView(redirectViewName);
    }

    /**
     * 微信授权回调链接
     *
     * @param code  code
     * @param state state
     * @param sn    商户SN
     * @return MV
     */
    @GetMapping("/auth/{merchantSn}")
    public ModelAndView auth(@RequestParam("code") String code, @RequestParam("state") String state,
                             @PathVariable("merchantSn") String sn) {
        final Merchant merchant = merchantService.findBySn(sn);
        if (merchant == null) {
            final String view = viewNameGenerator.errorPage("商户不存在");
            return redirectView(view);
        }
        final WechatUser user;
        try {
            user = wechatUserService.refreshUserInfo(merchant, code);
        } catch (Exception e) {
            log.error("Error on refresh user info from wechat server,code=[{}], state=[{}]!", code, state, e);
            final String view = viewNameGenerator.errorPage("ERROR:" + e.getMessage());
            return redirectView(view);
        }
        final UserToken token = encodeToken(user);
        return redirectToHome(token);
    }


    private UserToken encodeToken(WechatUser user) {
        return new UserToken(user, tokenConfiguration.getKey(), tokenConfiguration.getTtl());
    }

    private ModelAndView redirectToHome(UserToken token) {
        final Map<String, String> params = new HashMap<>(1);
        params.put("token", token.toTokenString());
        final String view = viewNameGenerator.home(params);
        return redirectView(view);
    }
}
