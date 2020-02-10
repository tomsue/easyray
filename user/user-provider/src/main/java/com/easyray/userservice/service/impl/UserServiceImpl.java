package com.easyray.userservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.easyray.userapi.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @Date: 20-1-27
 * @Author: wyy
 */
@Service(filter = {"userCheckPermissionFilter"})
@Component
public class UserServiceImpl extends UserLocalServiceImpl implements UserService {

}