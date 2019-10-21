package com.sdhh.service;

import com.sdhh.po.ActiveUser;
import com.sdhh.po.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author WangYing
 * @time 2019年7月23日下午3:47:07
 */
@Service
public interface UserService {

    public User createUser(User user); //创建账户

    public void changePassword(long userId, String newPassword);//修改密码

    public void correlationRoles(long userId, long... roleIds); //添加用户-角色关系

    public void uncorrelationRoles(long userId, long... roleIds);// 移除用户-角色关系

    // add custom
    public User findUserByName(String username);

    public ActiveUser findActiveUserByUsercode(String usercode);

    public Set<String> findRolesByName(String username);// 根据用户名查找其角色

    public Set<String> findPermissionsByName(String username); //根据用户名查找其权限

    public boolean userLogin(String username, String password);

    public ActiveUser findActiveUserByUsername(String username);    //增加roles,permissions

    public boolean matchPwdByUsername(String username, String password);

    public List<User> getUsers();

    public int updatePasswordById(long id, String newPwd, String salt);

    public int updatePasswordByUsername(String username, String newPwd, String salt);

    public int delUserById(long id);

    public List<User> getUserPerms();

    public List<User> getUserPermsByUsername(String username);

    public List<User> getUserPermsById(long id);

    public int addUser(User user);

    public int addPermsById(Long id, String add_permissions);

    public int delUserPermsById(Long id);

    /*public int updatePermsByUserid(Long id, String perms);*/

}
