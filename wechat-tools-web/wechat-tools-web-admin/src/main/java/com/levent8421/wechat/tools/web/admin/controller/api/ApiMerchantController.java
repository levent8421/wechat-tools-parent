package com.levent8421.wechat.tools.web.admin.controller.api;

import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import com.levent8421.wechat.tools.web.commons.vo.PaginationParam;
import org.springframework.web.bind.annotation.*;

import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notEmpty;
import static com.levent8421.wechat.tools.web.commons.util.ParamChecker.notNull;

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
    private final MerchantService merchantService;

    public ApiMerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * Create By Levent8421
     * Create Time: 2020/8/28 22:06
     * Class Name: ApiMerchantController
     * Author: Levent8421
     * Description:
     * 分页查询所有商户信息
     *
     * @author Levent8421
     */
    @GetMapping("/_paged")
    public GeneralResult<PageInfo<Merchant>> list(PaginationParam param) {
        final PageInfo<Merchant> merchants = merchantService.list(param.getPage(), param.getRows());
        return GeneralResult.ok(merchants);
    }

    /**
     * 创建新商户
     *
     * @param param 参数
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<Merchant> createMerchant(@RequestBody Merchant param) {
        final Merchant merchant = copyCreateParams(param);
        final Merchant res = merchantService.create(merchant);
        return GeneralResult.ok(res);
    }

    private Merchant copyCreateParams(Merchant param) {
        final Class<BadRequestException> exception = BadRequestException.class;
        notNull(param, exception, "空参数!");
        notEmpty(param.getName(), exception, "名称必填");
        notEmpty(param.getLoginName(), exception, "登录名必填");
        notEmpty(param.getPassword(), exception, "密码必填");
        final Merchant merchant = new Merchant();
        merchant.setName(param.getName());
        merchant.setLoginName(param.getLoginName());
        merchant.setPassword(param.getPassword());
        return merchant;
    }

    /**
     * find  merchant by row id
     *
     * @param id id
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<Merchant> findMerchantById(@PathVariable("id") Integer id) {
        final Merchant merchant = merchantService.require(id);
        return GeneralResult.ok(merchant);
    }

    /**
     * 绑定微信APP_ID
     *
     * @param id    merchant id
     * @param param params{wechatAppId,wechatSecret}
     * @return GRyarn start
     */
    @PostMapping("/{id}/wechat-app-id")
    public GeneralResult<Merchant> bindWechatAppId(@PathVariable("id") Integer id, @RequestBody Merchant param) {
        final Class<BadRequestException> error = BadRequestException.class;
        notNull(param, error, "参数为空！");
        notEmpty(param.getWechatAppId(), error, "微信APP_ID必填");
        notEmpty(param.getWechatSecret(), error, "微信SECRET必填");
        final Merchant merchant = merchantService.require(id);
        final Merchant res = merchantService.bindWechatAppId(merchant, param.getWechatAppId(), param.getWechatSecret());
        return GeneralResult.ok(res);
    }
}
