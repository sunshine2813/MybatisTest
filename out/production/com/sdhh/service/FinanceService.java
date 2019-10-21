package com.sdhh.service;

import com.sdhh.po.ActiveUser;
import com.sdhh.po.Finance;

import java.util.List;

/**
 * @author WangYing
 * @time 2019年7月4日上午11:20:57
 */

public interface FinanceService {

    int addFinance(Finance fin);

    void setUrl(Long dbId, String imageurl, String attachurl);

    List<Finance> getAllFinanceByActiveUser(ActiveUser activeUser);

    List<Finance> getTFinanceByActiveUser(String type, ActiveUser activeUser);

    List<Finance> getNFinanceByActiveUser(String name, ActiveUser activeUser);

    List<Finance> getTNFinanceByActiveUser(String type, String name, ActiveUser activeUser);

    int deleteFinanceById(long id);

    String getImagenamesById(Long dbId);

    int addImagenamesById(String newImagenames, Long dbId);

    String getAttachnamesById(Long dbId);

    int addAttachnamesById(String string, Long dbId);


}
