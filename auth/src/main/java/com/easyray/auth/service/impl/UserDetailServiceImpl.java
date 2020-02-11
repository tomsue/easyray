package com.easyray.auth.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.easyray.auth.entity.UserDetailsImpl;
import com.easyray.userapi.entity.User;
import com.easyray.userapi.service.UserLocalProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Date: 20-1-30
 * @Author: wyy
 */
@Component("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference(check = false)
    private UserLocalProvider userLocalService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userLocalService.fetchByUsername(s);
        if (user != null) {
            return new UserDetailsImpl(user);
        }
        return null;
    }
}
