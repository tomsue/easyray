package com.easyray.teamprovider.service.impl;

import com.easyray.baseapi.provider.CheckPermissionAspect;
import com.easyray.teamapi.service.TeamCheckPermission;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Date: 2020-02_22
 * @Author: wyy
 */
@Aspect
@Component
public class TeamCheckPermissionAspect extends CheckPermissionAspect<TeamCheckPermission> {

    @Autowired
    private TeamCheckPermission checkPermission;

    @Override
    public TeamCheckPermission getCheckPermission() {
        return checkPermission;
    }

    @Pointcut("target(TeamProviderImpl)")
    @Override
    public void pointcut() {
    }
}
