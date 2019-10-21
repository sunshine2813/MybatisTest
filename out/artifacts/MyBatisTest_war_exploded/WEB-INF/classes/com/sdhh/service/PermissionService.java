package com.sdhh.service;

import com.sdhh.po.Permission;

/**
 * @author WangYing
 * @time 2019年7月24日上午10:41:38
 */

public interface PermissionService {

    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
