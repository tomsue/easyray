package com.easyray.systemprovider.provider.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyray.systemapi.entity.Role;
import com.easyray.systemapi.entity.User;
import com.easyray.systemapi.entity.UserRole;
import com.easyray.systemapi.service.UserRoleCheckPermission;
import com.easyray.systemprovider.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2020-02_12
 * @Author: wyy
 */
@Component
public class UserRoleCheckPermissionImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleCheckPermission {

    @Override
    public List<User> findUserByRoleId(long roleId) {
        return null;
    }

    @Override
    public List<Role> findRoleByUserId(long userId) {
        return null;
    }
}
