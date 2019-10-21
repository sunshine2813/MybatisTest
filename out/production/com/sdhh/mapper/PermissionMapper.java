package com.sdhh.mapper;

import java.util.List;

import com.sdhh.po.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    // add custom
    List<Permission> findMenuListByUserId(Long userid);

    List<Permission> findPermissionListByUserId(Long userid);
}