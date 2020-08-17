package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.repository.mapper.MerchantMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.MerchantService;
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
    private final MerchantMapper merchantMapper;

    public MerchantServiceImpl(MerchantMapper merchantMapper) {
        super(merchantMapper);
        this.merchantMapper = merchantMapper;
    }
}
