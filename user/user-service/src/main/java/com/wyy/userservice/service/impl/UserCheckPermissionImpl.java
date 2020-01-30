package com.wyy.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyy.easyry.entity.User;
import com.wyy.easyry.service.UserCheckPermission;
import com.wyy.userservice.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @Date: 20-1-28
 * @Author: wyy
 */
@Component
public class UserCheckPermissionImpl extends ServiceImpl<UserMapper, User> implements UserCheckPermission {
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User fetchByUsername(String username) {
        return null;
    }
}
