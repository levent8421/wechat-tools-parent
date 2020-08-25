package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.vo.TokenAccountVo;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 9:47
 * Class Name: ApiMerchantController
 * Author: Levent8421
 * Description:
 * API Merchant Controller
 * token required
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/token/merchant")
public class ApiMerchantController extends AbstractController {
    /**
     * 管理员登录
     *
     * @param param params
     * @return token and account
     */
    @PostMapping("/_login")
    public GeneralResult<TokenAccountVo<Admin>> login(@RequestBody Admin param) {
        return null;
    }
}
