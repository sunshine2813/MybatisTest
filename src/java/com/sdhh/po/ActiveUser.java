package com.sdhh.po;

import java.io.Serializable;

/**
 * @author WangYing
 * @time 2019年8月2日上午10:54:10
 */
@SuppressWarnings("serial")
public class ActiveUser implements Serializable {
    private Long id;

    private String usercode;    //ls

    private String username;    //李四

    private String password;

    private String salt;        //random

    private String locked;

    private String roles;            //可以用userService直接查询roles,直接是list类型,这里先用逗号分隔的string
    private String permissions; //权限


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}