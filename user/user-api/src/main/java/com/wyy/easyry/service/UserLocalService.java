package com.wyy.easyry.service;

import com.wyy.common.exception.EasyCustomException;
import com.wyy.common.exception.EntityNotExistException;
import com.wyy.common.exception.NoPermissionException;
import com.wyy.baseapi.service.BaseLocalService;
import com.wyy.easyry.entity.User;

/**
 * @Date: 20-1-26
 * @Author: wyy
 */
public interface UserLocalService extends BaseLocalService<User> {

    public User findByUsername(String username) throws EntityNotExistException;

    public User fetchByUsername(String username);

    public void testException() throws NoPermissionException, EasyCustomException, EntityNotExistException;

}
