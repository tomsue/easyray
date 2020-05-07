package com.easyray.systemapi.service;

import com.easyray.baseapi.provider.BaseLocalProvider;
import com.easyray.systemapi.entity.Tenant;

/**
 * @Date: 2020-02_12
 * @Author: wyy
 */
public interface TenantLocalProvider extends BaseLocalProvider<Tenant> {

    public Tenant fetchByName(String name);

}
