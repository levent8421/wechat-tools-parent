package com.levent8421.wechat.tools.model.service.general.impl;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.entity.WechatUser;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.model.repository.mapper.WechatUserMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.WechatUserService;
import com.levent8421.wechat.tools.wechat.api.WechatApi;
import com.levent8421.wechat.tools.wechat.api.WechatConstants;
import com.levent8421.wechat.tools.wechat.api.util.WechatUtils;
import com.levent8421.wechat.tools.wechat.api.vo.WechatOauthToken;
import com.levent8421.wechat.tools.wechat.api.vo.WechatUserInfo;
import org.springframework.stereotype.Service;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 15:00
 * Class Name: WechatUserServiceImpl
 * Author: Levent8421
 * Description:
 * 微信用户相关业务行为实现
 *
 * @author Levent8421
 */
@Service
public class WechatUserServiceImpl extends AbstractServiceImpl<WechatUser> implements WechatUserService {
    private final WechatUserMapper wechatUserMapper;
    private final WechatApi wechatApi;

    public WechatUserServiceImpl(WechatUserMapper wechatUserMapper,
                                 WechatApi wechatApi) {
        super(wechatUserMapper);
        this.wechatUserMapper = wechatUserMapper;
        this.wechatApi = wechatApi;
    }

    @Override
    public WechatUser refreshUserInfo(Merchant merchant, String oAuthCode) {
        final WechatOauthToken oauthToken = fetchOauthToken(merchant, oAuthCode);
        final String openId = oauthToken.getOpenId();
        final WechatUser wechatUser = findOrCreateByOpenId(merchant, openId);
        return refreshUserInfo(merchant, wechatUser, oauthToken.getAccessToken());
    }

    private WechatOauthToken fetchOauthToken(Merchant merchant, String code) {
        final String appId = merchant.getWechatAppId();
        final String secret = merchant.getWechatSecret();
        final WechatOauthToken token = wechatApi.getOauthToken(appId, secret, code);
        if (!WechatUtils.isSuccess(token)) {
            throw new InternalServerErrorException("Can not fetch oAuthToken,");
        }
        return token;
    }

    @Override
    public WechatUser findByOpenId(Merchant merchant, String openId) {
        return wechatUserMapper.selectByOpenId(merchant.getId(), openId);
    }

    @Override
    public WechatUser findOrCreateByOpenId(Merchant merchant, String openId) {
        WechatUser user = findByOpenId(merchant, openId);
        if (user == null) {
            user = createDefaultUser(merchant, openId);
        }
        return user;
    }

    private WechatUser createDefaultUser(Merchant merchant, String openId) {
        final WechatUser user = new WechatUser();
        user.setMerchantId(merchant.getId());
        user.setWOpenId(openId);
        return save(user);
    }

    @Override
    public WechatUser refreshUserInfo(Merchant merchant, WechatUser user, String oAuthToken) {
        final WechatUserInfo wechatUserInfo = wechatApi.getUserInfo(oAuthToken, user.getWOpenId(), WechatConstants.DEFAULT_LANG);

        user.setWAvatar(wechatUserInfo.getHeadimgurl());
        user.setWNickname(wechatUserInfo.getNickname());
        user.setWUnionId(wechatUserInfo.getUnionid());
        user.setWMetadata(JSON.toJSONString(wechatUserInfo));
        return updateById(user);
    }
}
