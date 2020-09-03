package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.model.service.general.AdminService;
import com.levent8421.wechat.tools.web.admin.security.AdminToken;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/28 2:50
 * Class Name: ApiAdminController
 * Author: Levent8421
 * Description:
 * 管理员账户相关API数据访问控制器(需token验证)
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/admin")
public class ApiAdminController extends AbstractController {
    private final AdminService adminService;
    private final TokenDataHolder tokenDataHolder;

    public ApiAdminController(AdminService adminService, TokenDataHolder tokenDataHolder) {
        this.adminService = adminService;
        this.tokenDataHolder = tokenDataHolder;
    }

    /**
     * 当前登录的管理员账户信息
     *
     * @return GR
     */
    @GetMapping("/_me")
    public GeneralResult<Admin> currentAdminAccount() {
        final Integer adminId = tokenDataHolder.get(AdminToken.ADMIN_ID_NAME, Integer.class);
        final Admin admin = adminService.require(adminId);
        return GeneralResult.ok(admin);
    }
}
