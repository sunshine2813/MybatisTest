package com.sdhh.mapper;

import com.sdhh.po.Finance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Finance record);

    int insertSelective(Finance record);

    Finance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Finance record);

    int updateByPrimaryKeyWithBLOBs(Finance record);

    int updateByPrimaryKey(Finance record);

    // add custom
    int addFinance(Finance fin);

    void setUrl(@Param("dbId") Long dbId, @Param("imageurl") String imageurl, @Param("attachurl") String attachurl);

    List<Finance> getAllFinance();

    List<Finance> getAllFinanceByUsername(String username);

    List<Finance> getTFinance(String type);

    List<Finance> getTFinanceByUsername(String type, @Param("username") String username);

    List<Finance> getNFinance(String name);

    List<Finance> getNFinanceByUsername(String name, @Param("username") String username);

    List<Finance> getTNFinance(@Param("type") String type, @Param("name") String name);

    List<Finance> getTNFinanceByUsername(@Param("type") String type, @Param("name") String name, @Param("username") String username);

    int deleteFinanceById(long id);

    String getImagenamesById(Long dbId);

    int addImagenamesById(@Param("newImagenames") String newImagenames, @Param("dbId") Long dbId);

    String getAttachnamesById(Long dbId);

    int addAttachnamesById(@Param("newAttachnames") String newAttachnames, @Param("dbId") Long dbId);
}