package com.sdhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdhh.mapper.VoucherMapper;
import com.sdhh.po.Voucher;

/**
 * @author WangYing
 * @time 2019年7月2日上午10:10:53
 */
@Service("VoucherService")
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public List<Voucher> getCymVoucher(String company, int year, int month) {
        return voucherMapper.getCymVoucher(company, year, month);
    }

    @Override
    public List<Voucher> getCymnVoucher(String company, int year, int month, int number) {
        return voucherMapper.getCymnVoucher(company, year, month, number);
    }

    @Override
    public int addVoucher(String company, int year, int month, int number, String detail, String imageurl, String imagenames, String attachurl, String attachnames) throws Exception {
        return voucherMapper.addVoucher(company, year, month, number, detail, imageurl, imagenames, attachurl, attachnames);
    }

    public int deleteVoucherById(long id) {
        return voucherMapper.deleteVoucherById(id);
    }

    @Override
    public int addImgByInfo(String imagenames, String company, int year, int month, int number) {
        return voucherMapper.addImgByInfo(imagenames, company, year, month, number);
    }

    @Override
    public String getImagenamesByCYMN(String company, int year, int month, int number) {
        return voucherMapper.getImagenamesByCYMN(company, year, month, number);
    }


    @Override
    public int addAttachByInfo(String attachnames, String company, int year, int month, int number) {
        return voucherMapper.addAttachByInfo(attachnames, company, year, month, number);
    }

    @Override
    public String getAttachnamesByCYMN(String company, int year, int month, int number) {
        // TODO Auto-generated method stub
        return voucherMapper.getAttachnamesByCYMN(company, year, month, number);
    }


}
