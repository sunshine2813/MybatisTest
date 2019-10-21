package com.sdhh.mapper;

import com.sdhh.po.Role_permission;

public interface Role_permissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role_permission record);

    int insertSelective(Role_permission record);

    Role_permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role_permission record);

    int updateByPrimaryKey(Role_permission record);
}