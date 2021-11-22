package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.SuperCtlApp;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlAppMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlAppService;
import org.springframework.stereotype.Service;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlAppServiceImpl Class
 *
 * @author levent8421
 */
@Service
public class SuperCtlAppServiceImpl extends AbstractServiceImpl<SuperCtlApp> implements SuperCtlAppService {
    private final SuperCtlAppMapper superCtlAppMapper;

    public SuperCtlAppServiceImpl(SuperCtlAppMapper mapper) {
        super(mapper);
        this.superCtlAppMapper = mapper;
    }
}
