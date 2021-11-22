package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlDeviceMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
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
public class SuperCtlDeviceServiceImpl extends AbstractServiceImpl<SuperCtlDevice> implements SuperCtlDeviceService {
    private final SuperCtlDeviceMapper superCtlAppMapper;

    public SuperCtlDeviceServiceImpl(SuperCtlDeviceMapper mapper) {
        super(mapper);
        this.superCtlAppMapper = mapper;
    }
}
