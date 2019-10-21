package com.sdhh.mapper;

import com.sdhh.po.ActiveUser;
import com.sdhh.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // add custom

    User createUser(User user);

    void changePassword(@Param("id") long id, @Param("password") String md5Pwd, @Param("salt") String salt);

    ActiveUser findActiveUserByUsercode(String usercode);

    User findUserByName(String username);

    ActiveUser findActiveUserByUsername(String username);

    List<User> getUsers();

    List<User> getUserPerms();

    List<User> getUserPermsById(long id);

    List<User> getUserPermsByUsername(String username);

    int updatePasswordById(@Param("id") long id, @Param("newPwd") String newPwd, @Param("salt") String salt);

    int updatePasswordByUsername(@Param("username") String username, @Param("newPwd") String newPwd, @Param("salt") String salt);

    String getSaltByUsername(String username);

    int delUserById(long id);

/*    int addUser(@Param("username")String username, @Param("usercode")String usercode,
                @Param("password")String password,@Param("salt")String salt,
                @Param("add_permissions") List<String> add_permissions);*/

    int addUser(User user);

    /*list只包含user_id和permission_name 增加到表 user_permission中*/
    int addPermsById(ArrayList<HashMap> list);

    int delUserPermsById(Long id);

/*    int updatePermsByUserid(ArrayList<HashMap> list);*/

}