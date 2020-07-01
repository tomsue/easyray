package com.easyray.resourcepermission.service;

import com.easyray.baseapi.provider.BaseLocalProvider;
import com.easyray.common.exception.EntityDeleteFailedException;
import com.easyray.resourcepermission.entity.ResourceAction;

import java.util.List;

/**
 * @Date: 20-2-2
 * @Author: wyy
 */
public interface ResourceActionLocalProvider extends BaseLocalProvider<ResourceAction> {
    public List<ResourceAction> fetchByName(String entityName);

    public void deleteByNameAndActions(String entityName, List<String> actionList) throws EntityDeleteFailedException;

    public int countByName(String entityName);

    public List<ResourceAction> fetchByNameAndActions(String entityName, List<String> actionList);

    public ResourceAction fetchByNameAndAction(String entityName, String action);
}
