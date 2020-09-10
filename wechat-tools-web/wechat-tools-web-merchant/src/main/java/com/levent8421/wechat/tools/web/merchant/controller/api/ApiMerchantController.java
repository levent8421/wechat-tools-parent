package com.levent8421.wechat.tools.web.merchant.controller.api;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.security.TokenDataHolder;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.merchant.security.MerchantToken;
import org.springframework.web.bind.annotation.*;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

/**
 * Create By leven ont 2020/9/9 23:19
 * Class Name :[ApiMerchantController]
 * <p>
 * 商户相关API数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/merchant")
public class ApiMerchantController extends AbstractController {
    private final TokenDataHolder tokenDataHolder;
    private final MerchantService merchantService;

    public ApiMerchantController(TokenDataHolder tokenDataHolder,
                                 MerchantService merchantService) {
        this.tokenDataHolder = tokenDataHolder;
        this.merchantService = merchantService;
    }

    /**
     * 获取当前登录的商户
     *
     * @return GR
     */
    @GetMapping("/_me")
    public GeneralResult<Merchant> me() {
        final Integer merchantId = tokenDataHolder.get(MerchantToken.MERCHANT_ID_KEY, Integer.class);
        final Merchant merchant = merchantService.require(merchantId);
        return GeneralResult.ok(merchant);
    }

    /**
     * 配置微信信息
     *
     * @param id    id
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/_wechat-info")
    public GeneralResult<Merchant> setWechatInfo(@PathVariable("id") Integer id, @RequestBody Merchant param) {
        final Class<? extends RuntimeException> error = BadRequestException.class;
        notNull(param, error, "参数为空");
        notEmpty(param.getWechatAppId(), error, "请输入微信APP_ID");
        notEmpty(param.getWechatSecret(), error, "请输入微信SECRET");

        final Merchant merchant = merchantService.require(id);
        merchant.setWechatAppId(param.getWechatAppId());
        merchant.setWechatSecret(param.getWechatSecret());
        final Merchant res = merchantService.updateById(merchant);
        return GeneralResult.ok(res);
    }
}
