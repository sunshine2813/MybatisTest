package com.sdhh.service;

import com.sdhh.po.Contract;
import com.sdhh.po.ContractType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYing
 * @time 2019年7月4日上午11:18:06
 */

@Service
public interface ContractService {

    int addContract(Contract c1);

    void setUrl(Long dbId, String imageurl, String attachurl);

    int deleteContractById(long id);

    String getImagenamesById(Long dbId);

    int addImagenamesById(String newImagenames, Long dbId);

    String getAttachnamesById(Long dbId);

    int addAttachnamesById(String newAttachnames, Long dbId);

    List<Contract> getAllContract();

    List<Contract> getTNContract(String type, String name);

    List<Contract> getYNContract(int year, String name);

    List<Contract> getTYContract(int year, String type);

    List<Contract> getYContract(int year);

    List<Contract> getNContract(String name);

    List<Contract> getTContract(String type);

    List<Contract> getTYNContract(String type, int year, String name);

    List<ContractType> getContractType();

    int delCrtTypById(Long id);

    int addContractType(ContractType ct1);
}
