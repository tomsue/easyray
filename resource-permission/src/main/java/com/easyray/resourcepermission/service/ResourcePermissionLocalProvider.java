package com.easyray.resourcepermission.service;

import com.easyray.baseapi.provider.BaseLocalProvider;
import com.easyray.resourcepermission.entity.ResourcePermission;

import java.util.List;

/**
 * @Date: 20-2-2
 * @Author: wyy
 */
public interface ResourcePermissionLocalProvider extends BaseLocalProvider<ResourcePermission> {
    public List<ResourcePermission> fetchByName(String name);

    public ResourcePermission fetchByNameAndRoleId(String name, long roleId);

    public List<ResourcePermission> fetchPermission(long userId, long groupId, String action, String name);
}
