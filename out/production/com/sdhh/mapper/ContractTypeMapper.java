package com.sdhh.mapper;

import com.sdhh.po.ContractType;

public interface ContractTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContractType record);

    int insertSelective(ContractType record);

    ContractType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContractType record);

    int updateByPrimaryKey(ContractType record);
}