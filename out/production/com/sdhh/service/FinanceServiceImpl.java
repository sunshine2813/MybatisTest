package com.sdhh.service;

import com.sdhh.mapper.FinanceMapper;
import com.sdhh.po.ActiveUser;
import com.sdhh.po.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYing
 * @time 2019年7月4日上午11:21:22
 */
@Service("FinanceService")
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public int addFinance(Finance fin) {
        return financeMapper.addFinance(fin);
    }

    @Override
    public void setUrl(Long dbId, String imageurl, String attachurl) {
        financeMapper.setUrl(dbId, imageurl, attachurl);
    }

    @Override
    public List<Finance> getAllFinanceByActiveUser(ActiveUser activeUser) {
        if (activeUser.getPermissions().contains("finance:queryAll"))
            return financeMapper.getAllFinance();
        return financeMapper.getAllFinanceByUsername(activeUser.getUsername());
    }


    @Override
    public List<Finance> getTFinanceByActiveUser(String type, ActiveUser activeUser) {
//        如果有查看所有财务资料的权限,则不用限定username
        if (activeUser.getPermissions().contains("finance:queryAll"))
            return financeMapper.getTFinance(type);
        return financeMapper.getTFinanceByUsername(type, activeUser.getUsername());
    }

    @Override
    public List<Finance> getNFinanceByActiveUser(String name, ActiveUser activeUser) {
//        如果有查看所有财务资料的权限,则不用限定username
        if (activeUser.getPermissions().contains("finance:queryAll"))
            return financeMapper.getNFinance(name);
        return financeMapper.getNFinanceByUsername(name, activeUser.getUsername());
    }

    @Override
    public List<Finance> getTNFinanceByActiveUser(String type, String name, ActiveUser activeUser) {
//        如果有查看所有财务资料的权限,则不用限定username
        if (activeUser.getPermissions().contains("finance:queryAll"))
            return financeMapper.getTNFinance(type, name);
        return financeMapper.getTNFinanceByUsername(type, name, activeUser.getUsername());
    }

    @Override
    public int deleteFinanceById(long id) {
        return financeMapper.deleteFinanceById(id);
    }

    @Override
    public String getImagenamesById(Long dbId) {
        return financeMapper.getImagenamesById(dbId);
    }

    @Override
    public int addImagenamesById(String newImagenames, Long dbId) {
        return financeMapper.addImagenamesById(newImagenames, dbId);
    }

    @Override
    public String getAttachnamesById(Long dbId) {
        return financeMapper.getAttachnamesById(dbId);
    }

    @Override
    public int addAttachnamesById(String newAttachnames, Long dbId) {
        return financeMapper.addAttachnamesById(newAttachnames, dbId);
    }

}
