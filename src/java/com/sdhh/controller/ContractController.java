package com.sdhh.controller;

import com.sdhh.po.Contract;
import com.sdhh.service.ContractService;
import com.sdhh.util.FileLoad;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangYing
 * @time 2019年7月4日上午11:16:15
 */
@Controller
public class ContractController {
    private static Logger logger = Logger.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "/contractIndex")
    public String getcontractIndex() {
        logger.debug("<---------->" + "getcontractIndex进入合同主页面");
        return "contract/contractIndex";
    }


    /**
     * @Description: 1. 合同录入
     * 返回  year-type的所有合同信息
     * @Param: [name, type, number, year, month, partb, money, department, detail]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/addC", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addCAction(String name, String type, String number, int year, int month, String partb, BigDecimal money, String department, String detail) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //这里不判断是否已经存在相同的合同,合同的唯一性标识为:type,year,month,department,money,partb,number
        Contract cnt = new Contract(name, type, number, year, month, partb, money, department, detail);
        int code = contractService.addContract(cnt);
        if (0 == code) {
            logger.debug("<---------->ContractController---addC.fail失败---\nname:" + name + "type:" + type + ",year" + year + ",month:" + month + ",number:" + number + " ,detail:" + detail);
        }

        Long dbId = cnt.getId();
//		得到图片-附件的相对路径
        String imageurl = getRelUrl("contract", dbId, "img");
        String attachurl = getRelUrl("contract", dbId, "attach");
        // 更新 合同表的 imageurl 和 attachurl
        contractService.setUrl(dbId, imageurl, attachurl);
        //新建此合同对应的 img和attach目录
        // G:\IPFOV\contract\dbid\img
        // G:\IPFOV\contract\dbid\attach
        FileLoad.CrtDir(imageurl);
        FileLoad.CrtDir(attachurl);

//		获取当前 年-类型 的所有合同
        List<Contract> contracts = contractService.getTYContract(year, type);
        map.put("code", "1");
        map.put("data", contracts);
        return map;
    }

    /**
     * 1. 根据 年-类型 -合同名称   获取合同
     *
     * @param year 年
     * @param type 类型
     * @param name 合同名称
     * @return 获取合同
     * @throws Exception
     */
    @RequestMapping(value = "/getC", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCAction(int year, String type, String name) throws Exception {
        List<Contract> contracts = new ArrayList<Contract>();
        //		查询条件是:year, type, name
        // 查询条件全为空
        if ("0".equals(type) && "0".equals(name) && year == 0) {
            contracts = contractService.getAllContract();
        } else if ("0".equals(name) && year != 0 && !"0".equals(type)) {
            //只有name是空
            contracts = contractService.getTYContract(year, type);
        } else if (year == 0 && !"0".equals(type) && !"0".equals(name)) {
            // 只有year是空
            contracts = contractService.getTNContract(type, name);
        } else if ("0".equals(type) && year != 0 && !"0".equals(name)) {
            //只有 type是空
            contracts = contractService.getYNContract(year, name);
        } else if ("0".equals(type) && "0".equals(name) && year != 0) {
            //type name 是空
            contracts = contractService.getYContract(year);
        } else if ("0".equals(type) && year == 0 && !"0".equals(name)) {
            //type year是空
            contracts = contractService.getNContract(name);
        } else if ("0".equals(name) && year == 0) {
            //name year是空
            contracts = contractService.getTContract(type);
        } else {
//			全非空
            contracts = contractService.getTYNContract(type, year, name);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1");
        map.put("data", contracts);
        return map;
    }


    /**
     * 1. 删除合同
     * Param: [id, name, type, number, year, month, partb, money, department, imageurl, attachurl]
     * return: java.util.Map<java.lang.String,java.lang.Object>
     */
    //@RequiresPermissions(value = {"contract:delete"}, logical = Logical.OR)
    @RequestMapping(value = "/delC", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delC(long id, String name, String type, String number, int year, int month, String partb, BigDecimal money, String department, String imageurl, String attachurl) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<Contract> contracts = new ArrayList<Contract>();
        int delDbCode = contractService.deleteContractById(id);
        if (0 == delDbCode) {
            map.put("code", "0");
            map.put("data", contracts);
            logger.debug("<---------->delC-----未删除数据库记录.id:" + id);
            return map;
        }
//		返回当前    年-类型   的合同
        contracts = contractService.getTYContract(year, type);
//		删除此合同文件夹img attach 下的所有图片-附件
        int delFileCode = this.delVFileByUrl(imageurl, attachurl);
//		未成功删除图片-附件
        if (-1 == delFileCode) {
            map.put("code", "0");
            map.put("data", contracts);
            logger.debug("<---------->delC-----未删除合同的图片或附件.name:" + name + ", year:" + year + ", month:" + month + ",id:" + id);
            return map;
        } else {
            map.put("code", "1");
            map.put("data", contracts);
            return map;
        }
    }


    /**
     * 1. 上传图片与上传文件不同，上传图片后,前端调请求后端url:showImg2，返回图片缩略图(response的二进制流)
     *
     * @param multipartFile 文件
     * @param imageurl      //imageurl : encoded(contract\dbid\img)
     * @throws Exception
     * @return                            {code:,datetimeFileName:,encodedAbspath:}
     */
    //@RequiresPermissions(value = {"contract:create"}, logical = Logical.OR)
    @RequestMapping(value = "/uploadImageC", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadImageCAction(@RequestParam("file_data") MultipartFile multipartFile, @RequestParam("imageurl") String imageurl) throws Exception {
        Map<String, String> map = new HashMap<>();
        if (multipartFile != null) {
            FileLoad.uploadImage(multipartFile, imageurl, map);
            // 写入数据库imagenames字段  20190715105510_1_jpg
            String datetimeFileName = map.get("datetimeFileName");
            //根据imageurl解析出合同的基本信息year month number company
            String decodedImageurl = URLDecoder.decode(imageurl, "GBK");
            Long dbId = getIdByImageurl(decodedImageurl);

            // 更新数据库中的 imagenames 如何依据sql搞定 拼接字符串后 更新同一个字段?????
            String oldImagenames = contractService.getImagenamesById(dbId);
            String newImagenames = ("".equals(oldImagenames) || oldImagenames == null) ? datetimeFileName : (oldImagenames + "," + datetimeFileName);
            int code = contractService.addImagenamesById(newImagenames, dbId);
            if (-1 == code)
                logger.debug("<---------->fail======没有成功增加图片====uploadImageAction\n[oldImagenames+datetimeFileName, company, year, month, number]\n"
                        + oldImagenames + datetimeFileName + ",dbId:" + dbId);
        }
        return map;
    }

    /**
     * 1.点击超链接 下载attach img 通用方法
     *
     * @param request  请求
     * @param response 响应
     * @param abspath  G:\\IPFOV\\1\新建文本.txt
     * @throws Exception
     */
    //@RequiresPermissions(value = {"contract:query"}, logical = Logical.OR)
    @RequestMapping(value = "/downloadFileImgC.action", method = RequestMethod.GET)
    public void downloadFileImgAction(HttpServletRequest request, HttpServletResponse response, String abspath) throws Exception {
        FileLoad.downLoadFileImg(request, response, abspath);
    }

    /**
     * 1.用ajax formData方式(推荐)
     *
     * @param multipartFile 页面上传文件的格式
     * @param attachurl     文档url
     * @return // 	encodedAbspath 是为了点击超链接进行 下载
     * datetimeFileName 是为了显示文件名称
     * code 是flag
     * {
     * encodedAbspath=G%3A%5CIPFOV%5Cxxzx_voucher%5C2019-1-1%5Cattach%5C20190717085303-%E7%9C%81%E5%B1%80%E7%94%B5%E8%AF%9D%E8%B5%84%E6%96%9920180510.xlsx,
     * datetimeFileName=20190717085303-省局电话资料20180510.xlsx,
     * code=1
     * }
     * @throws Exception
     */
    //@RequiresPermissions(value = {"contract:create"}, logical = Logical.OR)
    @RequestMapping(value = "/uploadFileC", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFileAction(@RequestParam("file_data") MultipartFile multipartFile, @RequestParam("attachurl") String attachurl) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (multipartFile != null) {
            // 上传文件
            FileLoad.upload3(multipartFile, attachurl, map);

            // 写入数据库imagenames字段  20190715105510_1_jpg
            String datetimeFileName = (String) map.get("datetimeFileName");
            //根据imageurl解析出合同的基本信息year month number company
            String decodedAttachurl = URLDecoder.decode(attachurl, "GBK");
            Long dbId = getIdByImageurl(decodedAttachurl);

            // 更新数据库中的 imagenames 如何依据sql搞定 拼接字符串后 更新同一个字段?????
            String oldAttachnames = contractService.getAttachnamesById(dbId);
            String newAttachnames = ("".equals(oldAttachnames) || oldAttachnames == null) ? datetimeFileName : (oldAttachnames + "," + datetimeFileName);
            int code = contractService.addAttachnamesById(newAttachnames, dbId);
            if (-1 == code)
                logger.debug("<---------->fail======没有成功增加图片====uploadImageAction\n[oldImagenames+datetimeFileName, company, year, month, number]\n"
                        + oldAttachnames + datetimeFileName + ",dbId:" + dbId);
        }
        return map;
    }

    /**
     * 1.前端预览磁盘图片
     *
     * @param request  请求
     * @param response 响应
     * @param abspath  转义后的绝对路径,带%3A的
     */
    //@RequiresPermissions(value = {"contract:query"}, logical = Logical.OR)
    @RequestMapping(value = "/showImg2C", method = RequestMethod.GET)
    @ResponseBody
    public void showImg2Action(HttpServletRequest request, HttpServletResponse response, String abspath) {
        FileLoad.showImg(request, response, abspath);
    }


    /**
     * 1.根据 imageurl
     *
     * @param decodedImageurl 解码的url
     * @return 返回 合同的唯一标识 id
     */
    private Long getIdByImageurl(String decodedImageurl) {
        Long dbId = 1L;
        try {
            String osDeli = FileLoad.getFileDelimeterByOS();
            dbId = Long.parseLong(decodedImageurl.substring(decodedImageurl.indexOf(osDeli) + 1, decodedImageurl.indexOf(osDeli, decodedImageurl.indexOf(osDeli) + 1)));
            return dbId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbId;
    }


    /**
     * 1.删除对应目录下的image和attach目录 被 delC 调用
     *
     * @param imageurl  图片url
     * @param attachurl 文档url
     * @return 删除标记
     * @throws Exception
     */
    private int delVFileByUrl(String imageurl, String attachurl) throws Exception {
        if (imageurl != null && attachurl != null) {
            imageurl = URLDecoder.decode(imageurl, "GBK");
            attachurl = URLDecoder.decode(attachurl, "GBK");
            boolean codeImg = FileLoad.delFileByURL(imageurl);
            boolean codeAttach = FileLoad.delFileByURL(attachurl);
            if (codeImg && codeAttach)
                return 0;    //成功删除目录
        }
        return -1;
    }

    /**
     * 1.根据参数返回 合同的相对路径
     * 被方法 addCAction 调用
     *
     * @param function voucher or contract or finance
     * @param dbId     1
     * @param type     img or attach
     * @return 相对路径        \contract\1\img \contract\1\attach
     */
    private String getRelUrl(final String function, Long dbId, String type) throws Exception {
        String osDelimeter = FileLoad.getFileDelimeterByOS();
        return URLEncoder.encode(function + osDelimeter + dbId + osDelimeter + type + osDelimeter, "UTF-8");
    }

}



