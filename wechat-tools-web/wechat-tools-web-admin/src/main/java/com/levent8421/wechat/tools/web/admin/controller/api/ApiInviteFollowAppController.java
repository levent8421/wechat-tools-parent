package com.levent8421.wechat.tools.web.admin.controller.api;

import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.commons.vo.GeneralResult;
import org.springframework.web.bind.annotation.*;

/**
 * Create By leven ont 2020/9/22 23:59
 * Class Name :[ApiInviteFollowAppController]
 * <p>
 * 关注邀请APP相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/token/invite-follow-app")
public class ApiInviteFollowAppController extends AbstractController {
    private final InviteFollowAppService inviteFollowAppService;

    public ApiInviteFollowAppController(InviteFollowAppService inviteFollowAppService) {
        this.inviteFollowAppService = inviteFollowAppService;
    }

    /**
     * 更新APP状态
     *
     * @param id    id
     * @param param param
     * @return GR
     */
    @PostMapping("/{id}/state")
    public GeneralResult<InviteFollowApp> updateAppState(@PathVariable("id") Integer id,
                                                         @RequestBody InviteFollowApp param) {
        ParamChecker.notNull(param, BadRequestException.class, "No params!");
        ParamChecker.notNull(param.getState(), BadRequestException.class, "请指定状态");

        final InviteFollowApp app = inviteFollowAppService.require(id);
        app.setState(param.getState());
        final InviteFollowApp resApp = inviteFollowAppService.updateById(app);
        return GeneralResult.ok(resApp);
    }
}
