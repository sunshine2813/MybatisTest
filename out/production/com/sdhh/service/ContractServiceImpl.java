package com.sdhh.service;

import com.sdhh.mapper.ContractMapper;
import com.sdhh.po.Contract;
import com.sdhh.po.ContractType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYing
 * @time 2019年7月4日上午11:18:39
 */
@Service("ContractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public int addContract(Contract record) {
        return contractMapper.addContract(record);
    }

    @Override
    public void setUrl(Long dbId, String imageurl, String attachurl) {
        contractMapper.setUrl(dbId, imageurl, attachurl);

    }

    @Override
    public List<Contract> getTYContract(int year, String type) {
        return contractMapper.getTYContract(year, type);
    }

    @Override
    public List<Contract> getTYNContract(String type, int year, String name) {
        return contractMapper.getTYNContract(year, type, name);
    }

    @Override
    public int deleteContractById(long id) {
        return contractMapper.deleteContractById(id);
    }

    @Override
    public String getImagenamesById(Long dbId) {
        return contractMapper.getImagenamesById(dbId);
    }

    @Override
    public int addImagenamesById(String newImagenames, Long dbId) {
        return contractMapper.addImagenamesById(newImagenames, dbId);
    }

    @Override
    public String getAttachnamesById(Long dbId) {
        return contractMapper.getAttachnamesById(dbId);
    }

    @Override
    public int addAttachnamesById(String newAttachnames, Long dbId) {
        return contractMapper.addAttachnamesById(newAttachnames, dbId);
    }

    @Override
    public List<Contract> getAllContract() {
        return contractMapper.getAllContract();
    }

    @Override
    public List<Contract> getTNContract(String type, String name) {
        return contractMapper.getTNContract(type, name);
    }

    @Override
    public List<Contract> getYNContract(int year, String name) {
        return contractMapper.getYNContract(year, name);
    }

    @Override
    public List<Contract> getYContract(int year) {
        return contractMapper.getYContract(year);
    }

    @Override
    public List<Contract> getNContract(String name) {
        return contractMapper.getNContract(name);
    }

    @Override
    public List<Contract> getTContract(String type) {
        return contractMapper.getTContract(type);
    }

    @Override
    public List<ContractType> getContractType() {
        return contractMapper.getContractType();
    }

    @Override
    public int delCrtTypById(Long id) {
        return contractMapper.delCrtTypById(id);
    }

    @Override
    public int addContractType(ContractType ct1) {
        return contractMapper.addContractType(ct1);
    }

}
