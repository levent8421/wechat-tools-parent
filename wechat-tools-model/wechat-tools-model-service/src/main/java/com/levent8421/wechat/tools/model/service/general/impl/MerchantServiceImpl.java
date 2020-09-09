package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.commons.utils.encrypt.MD5Utils;
import com.levent8421.wechat.tools.model.repository.mapper.MerchantMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 0:47
 * Class Name: MerchantServiceImpl
 * Author: Levent8421
 * Description:
 * Merchant Service Impl
 * 商户业务组件实现
 *
 * @author Levent8421
 */
@Service
public class MerchantServiceImpl extends AbstractServiceImpl<Merchant> implements MerchantService {
    private static final int SALT_LENGTH = 5;
    private final MerchantMapper merchantMapper;
    private final SerialNumberGenerator serialNumberGenerator = new SerialNumberGenerator("M", "E", 4);

    public MerchantServiceImpl(MerchantMapper merchantMapper) {
        super(merchantMapper);
        this.merchantMapper = merchantMapper;
    }

    private String nextSn() {
        return serialNumberGenerator.next();
    }

    private String encodePassword(String password) {
        final String salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
        return MD5Utils.md5(password, salt);
    }

    @Override
    public Merchant create(Merchant merchant) {
        final String loginName = merchant.getLoginName();
        if (findByLoginName(loginName) != null) {
            throw new BadRequestException(String.format("登录民为[%s]的商户已经存在！", loginName));
        }
        merchant.setSn(nextSn());
        merchant.setAuthorizationCode("");
        merchant.setPassword(encodePassword(merchant.getPassword()));
        return save(merchant);
    }

    @Override
    public Merchant findByLoginName(String loginName) {
        final Merchant query = new Merchant();
        query.setLoginName(loginName);
        return findOneByQuery(query);
    }

    @Override
    public Merchant bindWechatAppId(Merchant merchant, String wechatAppId, String wechatSecret) {
        merchant.setWechatAppId(wechatAppId);
        merchant.setWechatSecret(wechatSecret);
        return updateById(merchant);
    }

    @Override
    public Merchant login(String loginName, String password) {
        final Merchant merchant = findByLoginName(loginName);
        if (merchant == null) {
            throw new PermissionDeniedException("商户不存在");
        }
        if (!checkPassword(merchant, password)) {
            throw new PermissionDeniedException("账户与密码不匹配");
        }
        return merchant;
    }

    private boolean checkPassword(Merchant merchant, String password) {
        return MD5Utils.isMatched(merchant.getPassword(), password);
    }
}
