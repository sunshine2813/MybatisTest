package com.sdhh.po;

import java.io.Serializable;

/**
 * @author wy
 * @Description 用户身份信息，存入Session  由于Tomcat正常关闭时会将Session序列化的本地硬盘上，所以实现Serializable接口
 * @date 2019年7月23日
 */
@SuppressWarnings("serial")
public class User implements Serializable {
    private Long id;

    private String usercode;    //ls

    private String username;    //李四

    private String password;

    private String salt;        //random

    private String locked;

    public User() {
    }

    ;

    public User(String username, String usercode, String password, String salt, String locked) {
        this.username = username;
        this.usercode = usercode;
        this.password = password;
        this.salt = salt;
        this.locked = locked;
    }

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
}