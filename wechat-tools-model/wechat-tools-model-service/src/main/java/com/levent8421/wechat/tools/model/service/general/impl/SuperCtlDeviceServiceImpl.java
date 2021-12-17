package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.repository.mapper.SuperCtlDeviceMapper;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Levent8421
 * Date: 2021/11/22 21:08
 * ClassName: SuperCtlAppServiceImpl
 * Description:
 * SuperCtlDeviceServiceImpl Class
 *
 * @author levent8421
 */
@Slf4j
@Service
public class SuperCtlDeviceServiceImpl extends AbstractServiceImpl<SuperCtlDevice> implements SuperCtlDeviceService {
    private final SuperCtlDeviceMapper superCtlAppMapper;

    public SuperCtlDeviceServiceImpl(SuperCtlDeviceMapper mapper) {
        super(mapper);
        this.superCtlAppMapper = mapper;
    }

    @Override
    public List<SuperCtlDevice> findByUser(Integer uid) {
        SuperCtlDevice query = new SuperCtlDevice();
        query.setWechatUserId(uid);
        return findByQuery(query);
    }

    @Override
    public void updateStatus(Integer id, SuperCtlDeviceStatus status) {
        String statusStr = status.asString();
        int rows = superCtlAppMapper.updateStatus(id, statusStr);
        if (rows != 1) {
            log.error("update statue[{}] for device[{}] res(Unexpect)=[{}]", status, id, rows);
        } else {
            log.info("update statue[{}] for device[{}] res=[{}]", status, id, rows);
        }
    }

    @Override
    public void updateStatus(String sn, SuperCtlDeviceStatus status) {
        String statusStr = status.asString();
        int rows = superCtlAppMapper.updateStatusBySn(sn, statusStr);
        if (rows != 1) {
            log.error("update statue[{}] for device[{}] res(Unexpect)=[{}]", status, sn, rows);
        } else {
            log.info("update statue[{}] for device[{}] res=[{}]", status, sn, rows);
        }
    }

    @Override
    public SuperCtlDevice bindNewDevice(SuperCtlDevice device) {
        SuperCtlDevice dbDevice = findBySn(device.getSn());
        if (dbDevice != null) {
            throw new BadRequestException("该设备已被认领:" + dbDevice.getDeviceName());
        }
        return save(device);
    }

    @Override
    public SuperCtlDevice findBySn(String sn) {
        return superCtlAppMapper.selectBySn(sn);
    }
}
