package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlActionMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import org.springframework.stereotype.Service;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlDeviceServiceImpl Class
 *
 * @author levent8421
 */
@Service
public class SuperCtlActionServiceImpl extends AbstractServiceImpl<SuperCtlAction> implements SuperCtlActionService {
    private final SuperCtlActionMapper superCtlAppMapper;

    public SuperCtlActionServiceImpl(SuperCtlActionMapper mapper) {
        super(mapper);
        this.superCtlAppMapper = mapper;
    }
}
