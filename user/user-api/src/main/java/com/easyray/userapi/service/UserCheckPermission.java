package com.easyray.userapi.service;

import com.easyray.baseapi.provider.BaseCheckerPermission;
import com.easyray.userapi.entity.User;

/**
 * @Date: 20-1-28
 * @Author: wyy
 */
public interface UserCheckPermission extends BaseCheckerPermission<User>, UserLocalProvider {
}
