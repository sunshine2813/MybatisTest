package com.sdhh.service;

import com.sdhh.po.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYing
 * @time 2019年7月2日上午8:49:36
 */

@Service
public interface VoucherService {

    /**
     * 录入凭单
     *
     * @param company 单位
     * @param year    年份
     * @param month   月份
     * @param number  凭单编号
     * @return
     * @throws Exception
     */
    public int addVoucher(String company, int year, int month, int number, String detail,
                          String imageurl, String imagenames, String attachurl, String attachnames) throws Exception;


    /**
     * 查询条件是:单位-年-月
     *
     * @param company
     * @param year
     * @param month
     * @return
     */
    public List<Voucher> getCymVoucher(String company, int year, int month);

    /**
     * 查询条件是:单位-年份-月份-凭单号
     *
     * @param company
     * @param year
     * @param month
     * @param number
     * @return
     */
    public List<Voucher> getCymnVoucher(String company, int year, int month, int number);

    /**
     * 根据id删除凭单数据库记录
     *
     * @param id
     * @return
     */
    public int deleteVoucherById(long id);

    /**
     * 根据凭单的公司-年-月-编号增加图片名称
     *
     * @param company
     * @param year
     * @param month
     * @param number
     * @return
     */
    public int addImgByInfo(String imagenames, String company, int year, int month, int number);

    /**
     * 根据公司-年-月-编号（凭单的基本信息）获取之前已有的图片名称
     *
     * @param company
     * @param year
     * @param month
     * @param number
     * @return
     */
    public String getImagenamesByCYMN(String company, int year, int month, int number);


    public String getAttachnamesByCYMN(String company, int year, int month, int number);

    public int addAttachByInfo(String imagenames, String company, int year, int month, int number);

}
