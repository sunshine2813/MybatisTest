package com.sdhh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdhh.po.Voucher;

public interface VoucherMapper {
//	custom
//	@Param("company") 将这里的形参company1与sql语句的条件参数绑定

    List<Voucher> getCymVoucher(@Param("company") String c, @Param("year") int year, @Param("month") int month);


    List<Voucher> getCymnVoucher(@Param("company") String c, @Param("year") int year, @Param("month") int month, @Param("number") int number);

    int addVoucher(@Param("company") String company, @Param("year") int year, @Param("month") int month, @Param("number") int number, @Param("detail") String detail,
                   @Param("imageurl") String imageurl, @Param("imagenames") String imagenames, @Param("attachurl") String attachurl, @Param("attachnames") String attachnames);


    int deleteVoucherById(long id);

    int addImgByInfo(@Param("imagenames") String imagenames, @Param("company") String company, @Param("year") int year, @Param("month") int month, @Param("number") int number);

    String getImagenamesByCYMN(@Param("company") String company, @Param("year") int year, @Param("month") int month, @Param("number") int number);


    int addAttachByInfo(@Param("attachnames") String attachnames, @Param("company") String company, @Param("year") int year, @Param("month") int month, @Param("number") int number);

    String getAttachnamesByCYMN(@Param("company") String company, @Param("year") int year, @Param("month") int month, @Param("number") int number);


    //	end of custom
    int deleteByPrimaryKey(Long id);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKeyWithBLOBs(Voucher record);

    int updateByPrimaryKey(Voucher record);
}