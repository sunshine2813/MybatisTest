package com.sdhh.mapper;

import com.sdhh.po.Contract;
import com.sdhh.po.ContractType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ContractMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKeyWithBLOBs(Contract record);

    int updateByPrimaryKey(Contract record);

    // ADD custom
    int addContract(Contract record);

    void setUrl(@Param("dbId") Long dbId, @Param("imageurl") String imageurl, @Param("attachurl") String attachurl);

    int deleteContractById(long id);

    String getImagenamesById(Long id);

    int addImagenamesById(@Param("newImagenames") String newImagenames, @Param("id") Long id);

    String getAttachnamesById(Long id);

    int addAttachnamesById(@Param("newAttachnames") String newAttachnames, @Param("id") Long id);

    List<Contract> getAllContract();

    List<Contract> getTYContract(@Param("year") int year, @Param("type") String type);

    List<Contract> getTNContract(@Param("type") String type, @Param("name") String name);

    List<Contract> getYNContract(@Param("year") int year, @Param("name") String name);

    List<Contract> getYContract(int year);

    List<Contract> getNContract(String name);

    List<Contract> getTContract(String type);

    List<Contract> getTYNContract(@Param("year") int year, @Param("type") String type, @Param("name") String name);

    List<ContractType> getContractType();

    int delCrtTypById(Long id);

    int addContractType(ContractType record);

    //END add custom
}