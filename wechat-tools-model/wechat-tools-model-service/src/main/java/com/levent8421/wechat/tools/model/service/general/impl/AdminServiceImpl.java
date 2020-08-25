package com.levent8421.wechat.tools.model.service.general.impl;

import com.levent8421.wechat.tools.commons.entity.Admin;
import com.levent8421.wechat.tools.commons.exception.PermissionDeniedException;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.commons.utils.encrypt.MD5Utils;
import com.levent8421.wechat.tools.model.repository.mapper.AdminMapper;
import com.levent8421.wechat.tools.model.service.basic.impl.AbstractServiceImpl;
import com.levent8421.wechat.tools.model.service.general.AdminService;
import org.springframework.stereotype.Service;

/**
 * Create By Levent8421
 * Create Time: 2020/8/25 23:54
 * Class Name: AdminServiceImpl
 * Author: Levent8421
 * Description:
 * Admin Service 管理员账户相关业务组件实现
 *
 * @author Levent8421
 */
@Service
public class AdminServiceImpl extends AbstractServiceImpl<Admin> implements AdminService {
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        super(adminMapper);
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin login(String loginName, String password) {
        final Admin admin = findByLoginName(loginName);
        if (admin == null) {
            throw new ResourceNotFoundException(String.format("Account [%s] not find!", loginName));
        }
        final String encryptedPassword = admin.getPassword();
        if (!comparePassword(encryptedPassword, password)) {
            throw new PermissionDeniedException("The loginName does not match the password!");
        }
        return admin;
    }

    private boolean comparePassword(String encrypted, String password) {
        return MD5Utils.isMatched(encrypted, password);
    }

    @Override
    public Admin findByLoginName(String loginName) {
        return adminMapper.selectByLoginName(loginName);
    }
}
