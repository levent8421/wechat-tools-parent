package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.AdminService;
import com.levent8421.wechat.tools.web.admin.security.AdminToken;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

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
    private static final String ROOT_ADMIN = "root";
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

    /**
     * 全部管理员
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Admin>> listAll() {
        final List<Admin> admins = adminService.all();
        return GeneralResult.ok(admins);
    }

    /**
     * 创建管理员
     *
     * @param param admin
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<Admin> create(@RequestBody Admin param) {
        final Admin admin = new Admin();
        checkAndCopyCreateParam(admin, param);
        final Admin existsAdmin = adminService.findByLoginName(admin.getLoginName());
        if (existsAdmin != null) {
            throw new BadRequestException("账户" + admin.getLoginName() + "已存在");
        }
        final Admin resAdmin = adminService.create(admin);
        return GeneralResult.ok(resAdmin);
    }

    private void checkAndCopyCreateParam(Admin admin, Admin param) {
        final Class<BadRequestException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getName(), error, "请输入账户名");
        notEmpty(param.getLoginName(), error, "请输入登录名");
        notEmpty(param.getPassword(), error, "请输入密码");

        admin.setLoginName(param.getLoginName());
        admin.setName(param.getName());
        admin.setPassword(param.getPassword());
    }

    /**
     * 删除管理员账户
     *
     * @param id id
     * @return GR
     */
    @DeleteMapping("/{id}")
    public GeneralResult<Void> delete(@PathVariable("id") Integer id) {
        final Admin admin = adminService.require(id);
        if (Objects.equals(admin.getName(), ROOT_ADMIN)) {
            throw new BadRequestException("root账户不能被删除!");
        }
        adminService.deleteById(id);
        return GeneralResult.ok();
    }
}
