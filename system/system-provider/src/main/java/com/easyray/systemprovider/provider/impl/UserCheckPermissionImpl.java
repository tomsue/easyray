package com.easyray.systemprovider.provider.impl;

import com.easyray.baseapi.provider.EasyrayServiceImpl;
import com.easyray.systemapi.entity.User;
import com.easyray.systemapi.service.UserCheckPermission;
import com.easyray.systemprovider.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @Date: 20-1-28
 * @Author: wyy
 */
@Component
public class UserCheckPermissionImpl extends EasyrayServiceImpl<UserMapper, User> implements UserCheckPermission {
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User fetchByUsername(String username) {
        return null;
    }
}
