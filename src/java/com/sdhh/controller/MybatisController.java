package com.sdhh.controller;

import com.sdhh.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by admin on 2019/10/18.
 */

@Controller
public class MybatisController {
    private static Logger logger = Logger.getLogger(MybatisController.class);

    @Autowired
    private MybatisService mybatisService;

    @RequestMapping(value = "/tomybatis", method = RequestMethod.GET)
    public String tomybatis() {
        logger.debug("----------------" + "跳转到mybatisIndex" + "-------------");
        return "mybatis/mybatisIndex";
    }

    @RequestMapping(value = "/getOneToMore.action", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOneToMore(){
        Map<String, Object> map = new HashMap<>();


        return map;
    }
}
