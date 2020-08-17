package com.levent8421.wechat.tools.web.user.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractApiController;
import com.levent8421.wechat.tools.web.vo.GeneralResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 1:04
 * Class Name: ApiMerchantController
 * Author: Levent8421
 * Description:
 * 商户前台API相关数据访问控制器
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/api/merchant")
public class ApiMerchantController extends AbstractApiController {
    private final MerchantService merchantService;

    public ApiMerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * List all merchants
     *
     * @return GR
     */
    @GetMapping("/")
    public GeneralResult<List<Merchant>> list() {
        final List<Merchant> merchants = merchantService.all();
        return GeneralResult.ok(merchants);
    }
}
