package com.levent8421.wechat.tools.web.admin.controller.open;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.AdminService;
import com.levent8421.wechat.tools.web.admin.security.AdminToken;
import com.levent8421.wechat.tools.web.commons.conf.TokenConfiguration;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.vo.TokenAccountVo;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 9:49
 * Class Name: OpenAdminController
 * Author: Levent8421
 * Description:
 * 开放访问的管理员账户控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/open/admin")
public class OpenAdminController extends AbstractController {
    private final AdminService adminService;
    private final TokenConfiguration tokenConfiguration;

    public OpenAdminController(AdminService adminService,
                               TokenConfiguration tokenConfiguration) {
        this.adminService = adminService;
        this.tokenConfiguration = tokenConfiguration;
    }

    /**
     * 管理员登录
     *
     * @param param {loginName, password}
     * @return account info with token
     */
    @PostMapping("/_login")
    public GeneralResult<TokenAccountVo<Admin>> login(@RequestBody Admin param) {
        final Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "No param!");
        final String loginName = param.getLoginName();
        final String password = param.getPassword();
        ParamChecker.notNull(loginName, e, "login name required!");
        ParamChecker.notNull(password, e, "password required!");

        final Admin admin = adminService.login(loginName, password);
        final String token = encodeAsToken(admin);
        final TokenAccountVo<Admin> accountVo = new TokenAccountVo<>();
        accountVo.setAccount(admin);
        accountVo.setToken(token);
        return GeneralResult.ok(accountVo);
    }

    private String encodeAsToken(Admin admin) {
        final String tokenKey = tokenConfiguration.getKey();
        return new AdminToken(admin, tokenKey).toTokenString();
    }
}
