package com.easyray.systemprovider.provider.impl;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.easyray.baseapi.provider.BaseCheckPermissionFilter;
import com.easyray.systemapi.service.RoleCheckPermission;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Date: 2020-02_08
 * @Author: wyy
 */
@Component
public class RoleCheckPermissionFilter extends BaseCheckPermissionFilter<RoleCheckPermission> {

    @Override
    public RoleCheckPermission getCheckPermission() {
        ApplicationContext applicationContext = ServiceBean.getSpringContext();
        return applicationContext.getBean(RoleCheckPermission.class);
    }
}
