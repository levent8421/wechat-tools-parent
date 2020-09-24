package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.context.MerchantAppState;
import com.levent8421.wechat.tools.commons.entity.InviteFollowApp;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.repository.mapper.InviteFollowAppMapper;
import com.levent8421.wechat.tools.model.service.authorization.MerchantAuthorization;
import com.levent8421.wechat.tools.model.service.authorization.MerchantAuthorizationUtils;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.InviteFollowAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/9/16 11:04
 * Class Name: InviteFollowAppServiceImpl
 * Author: Levent8421
 * Description:
 * Invite follow app service implementation
 *
 * @author Levent8421
 */
@Service
@Slf4j
public class InviteFollowAppServiceImpl extends AbstractServiceImpl<InviteFollowApp> implements InviteFollowAppService {
    private static final String DEFAULT_IMAGE_NAME = "default.png";
    private final InviteFollowAppMapper inviteFollowAppMapper;

    public InviteFollowAppServiceImpl(InviteFollowAppMapper inviteFollowAppMapper) {
        super(inviteFollowAppMapper);
        this.inviteFollowAppMapper = inviteFollowAppMapper;
    }

    @Override
    public List<InviteFollowApp> findByMerchant(Integer merchantId) {
        final InviteFollowApp query = new InviteFollowApp();
        query.setMerchantId(merchantId);
        return findByQuery(query);
    }

    private void checkPermission(String authCode) {
        final boolean canCreate =
                MerchantAuthorizationUtils.checkPermission(authCode, MerchantAuthorization.AUTH_CREATE_INVITE_FOLLOW_APP);
        if (!canCreate) {
            throw new BadRequestException("您暂无权限操作该功能！");
        }
    }

    @Override
    public InviteFollowApp createApp(InviteFollowApp app, Merchant merchant) {
        checkPermission(merchant.getAuthorizationCode());
        app.setButtonImage(DEFAULT_IMAGE_NAME);
        app.setState(MerchantAppState.STATE_INIT);
        app.setPhoneRequired(false);
        app.setDefaultApp(false);
        app.setMerchantId(merchant.getId());
        return save(app);
    }

    @Override
    public void cancelMerchantDefaultApp(Integer merchantId) {
        final int rows = inviteFollowAppMapper.updateDefaultAppByMerchant(merchantId, false);
        log.debug("Cancel [{}] default inviteFollowApp(s)!", rows);
    }

    @Override
    public InviteFollowApp setDefaultAppFlag(InviteFollowApp app, Boolean defaultApp) {
        app.setDefaultApp(defaultApp);
        return updateById(app);
    }

    @Override
    public List<InviteFollowApp> findByMerchantAndState(Integer merchantId, Integer state) {
        final InviteFollowApp query = new InviteFollowApp();
        query.setMerchantId(merchantId);
        query.setState(state);
        return findByQuery(query);
    }
}
