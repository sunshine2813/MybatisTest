package com.sdhh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sdhh.po.Permission;
import com.sdhh.po.User;

/**
 * 认证授权服务接口
 *
 * @author wy
 */

@Service
public interface SysService {
    //根据用户的身份和密码进行认证，如果认证通过，返回用户身份信息
    public User authenticat(String usercode, String password) throws Exception;

    //根据用户账号查询用户信息
    public User findUserByUserCode(String userCode) throws Exception;

    //根据用户id查询权限范围内的菜单
    public List<Permission> findMenuListByUserId(Long userid) throws Exception;

    //根据用户id查询权限范围内的url
    public List<Permission> findPermissionListByUserId(Long userid) throws Exception;
}
