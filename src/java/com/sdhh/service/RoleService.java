package com.sdhh.service;

import com.sdhh.po.Role;

/**
 * @author WangYing
 * @time 2019年7月24日上午10:42:38
 */

public interface RoleService {
    public Role createRole(Role role);

    public void deleteRole(Long roleId);

    //添加角色-权限之间关系
    public void correlationPermissions(Long roleId, Long... permissionIds);

    //移除角色-权限之间关系
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
