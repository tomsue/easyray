package com.easyray.systemprovider.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.easyray.baseapi.provider.EasyrayServiceImpl;
import com.easyray.coreapi.entity.Role;
import com.easyray.coreapi.entity.User;
import com.easyray.coreapi.entity.UserRole;
import com.easyray.coreapi.service.UserRoleLocalProvider;
import com.easyray.systemprovider.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wyy
 * @since 2020-02_12
 */
@Service
@Component
public class UserRoleLocalProviderImpl extends EasyrayServiceImpl<UserRoleMapper, UserRole> implements UserRoleLocalProvider {


    @Override
    public List<User> findUserByRoleId(long roleId) {
        return getBaseMapper().findUserByRoleId(roleId);
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        return getBaseMapper().findRoleByUserId(userId);
    }
}
