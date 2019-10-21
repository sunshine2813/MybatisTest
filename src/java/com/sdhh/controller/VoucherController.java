package com.sdhh.controller;

import com.sdhh.po.Voucher;
import com.sdhh.service.VoucherService;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangYing
 * @time 2019年7月2日上午8:46:05
 */

@Controller
public class VoucherController {
    private static Logger logger = Logger.getLogger(VoucherController.class);

    @Autowired
    private VoucherService voucherService;


    /**
     * 1. 点击左侧的导航栏 iframe显示为凭单主页面
     *
     * @return
     */
   // @RequiresPermissions(value = {"voucher:query"}, logical = Logical.OR)
    @RequestMapping(value = "/voucherIndex")
    public String getVoucherIndex() {
        logger.debug("<---------->getVoucherIndex进入凭单主页面");
        return "voucher/voucherIndex";
    }

    /**
     * 1.凭单录入
     * 返回此 单位-年月-下的所有凭单
     *
     * @param company 凭单所属公司
     * @param year    年份
     * @param month   月份
     * @param number  凭单编号
     * @return
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:create"}, logical = Logical.OR)
    @RequestMapping(value = "/addV", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addVAction(String company, int year, int month, int number, String detail) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询新录入凭单是否已经存在,如果存在,则返回map.code=0,前端提示
        List<Voucher> list = voucherService.getCymnVoucher(company, year, month, number);
        if (list.size() > 0) {
            map.put("code", "0");
            return map;
        }
        String companyCode = "xxzx";
        if ("0".equals(company)) {
            company = "山东黄河信息中心";
        } else if ("1".equals(company)) {
            companyCode = "hxc";
            company = "山东和信成信息中心";
        }
//		得到图片-附件的相对路径
        String imageurl = getRelUrl(companyCode, "voucher", getIdByYearMonthNumber(year, month, number), "img");
        String attachurl = getRelUrl(companyCode, "voucher", getIdByYearMonthNumber(year, month, number), "attach");

        //新建此凭单对应的 img和attach目录
        // G:\IPFOV\xxzx_voucher\img
        // G:\IPFOV\xxzx_voucher\attach
        FileLoad.CrtDir(imageurl);
        FileLoad.CrtDir(attachurl);

//		获取图上传的片名称-上传的附件名称
        String imagenames = "";
        String attachnames = "";
        int code = voucherService.addVoucher(company, year, month, number, detail, imageurl, imagenames, attachurl, attachnames);
        if (0 == code) {
            logger.debug("<---------->---VoucherController---addV.fail---\ncompany:" + company + ",year" + year + ",month:" + month + ",number:" + number + " ,detail:" + detail);
        }
//		获取当前公司-年-月的所有凭单 编号是0(不查询编号)

        map = this.getV(company, year, month, 0);
        return map;
    }

    /**
     * 1. 根据 公司-年-月-凭单编号 获取凭单
     *
     * @param company 凭单所属公司
     * @param year    年份
     * @param month   月份
     * @param number  凭单编号
     * @return
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:query"}, logical = Logical.OR)
    @RequestMapping(value = "/getV", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getV(String company, int year, int month, int number) throws Exception {
        List<Voucher> cymVouchers = new ArrayList<Voucher>();
        //		查询条件是:company,year,month
        if (number == 0)
            cymVouchers = voucherService.getCymVoucher(company, year, month);
        else if (number != 0)
            cymVouchers = voucherService.getCymnVoucher(company, year, month, number);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1");
        map.put("data", cymVouchers);
        return map;
    }


    /**
     * 1. 删除凭单
     *
     * @param id        删除数据库记录
     * @param company   用于返回凭单的参数
     * @param year      用于返回凭单的参数
     * @param month     用于返回凭单的参数
     * @param number    用于返回凭单的参数
     * @param imageurl  删除文件
     * @param attachurl 删除文件
     * @return
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:delete"}, logical = Logical.OR)
    @RequestMapping(value = "/delV", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delV(long id, String company, int year, int month, int number, String imageurl, String attachurl) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Voucher> cymVouchers = new ArrayList<Voucher>();
        int delDbCode = voucherService.deleteVoucherById(id);
        if (0 == delDbCode) {
            map.put("code", "0");
            map.put("data", cymVouchers);
            logger.debug("<---------->delV失败-----未删除数据库记录.id:" + id);
            return map;
        }
//		返回当前公司-年-月的凭单
        cymVouchers = voucherService.getCymVoucher(company, year, month);
//		删除此凭单文件夹img attach 下的所有图片-附件
        int delFileCode = this.delVFileByCYMN(imageurl, attachurl);
//		未成功删除图片-附件
        if (-1 == delFileCode) {
            map.put("code", "0");
            map.put("data", cymVouchers);
            logger.debug("<---------->delV失败-----未删除凭单的图片或附件.company:" + company + ", year:" + year + ", month:" + month + ",id:" + id);
            return map;
        } else {
            map.put("code", "1");
            map.put("data", cymVouchers);
            return map;
        }
    }


    /**
     * 1.
     * 上传图片与上传文件不同，上传图片后,前端调请求后端url:showImg2，返回图片缩略图(response的二进制流)
     *
     * @param multipartFile 文件
     * @param imageurl      //imageurl : encoded(xxzx_voucher\2019-1-1\img)
     * @return
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:create"}, logical = Logical.OR)
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadImageAction(@RequestParam("file_data") MultipartFile multipartFile, @RequestParam("imageurl") String imageurl) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (multipartFile != null) {
            FileLoad.uploadImage(multipartFile, imageurl, map);
            // 写入数据库imagenames字段  20190715105510_1_jpg
            String datetimeFileName = map.get("datetimeFileName");
            //根据imageurl解析出凭单的基本信息year month number company
            String decodedImageurl = URLDecoder.decode(imageurl, "GBK");
            Map<String, String> cfymn = new HashMap<String, String>();
            getCfymnByImageurl(decodedImageurl, cfymn);
            String company = cfymn.get("company");
            int year = Integer.parseInt(cfymn.get("year"));
            int month = Integer.parseInt(cfymn.get("month"));
            int number = Integer.parseInt(cfymn.get("number"));
            String oldImagenames = voucherService.getImagenamesByCYMN(company, year, month, number);
            String newImagenames = ("".equals(oldImagenames) || oldImagenames == null) ? datetimeFileName : (oldImagenames + "," + datetimeFileName);
            int code = voucherService.addImgByInfo(newImagenames, company, year, month, number);
            if (-1 == code)
                logger.debug("<---------->fail失败======没有成功增加图片====uploadImageAction\n[oldImagenames+datetimeFileName, company, year, month, number]\n"
                        + oldImagenames + datetimeFileName + "," + company + "," + year + "," + month + "," + number);
        }

        return map;
    }


    /**
     * 1.2前端预览磁盘图片
     *
     * @param request  请求
     * @param response 响应
     * @param abspath  转义后的绝对路径,带%3A的
     * @throws UnsupportedEncodingException
     */
   // @RequiresPermissions(value = {"voucher:query"}, logical = Logical.OR)
    @RequestMapping(value = "/showImg2")
    @ResponseBody
    public void showImg2Action(HttpServletRequest request, HttpServletResponse response, String abspath) throws UnsupportedEncodingException {
        FileLoad.showImg(request, response, abspath);
    }


    /**
     * 凭单图片查看,合同图片查看,财务资料图片查看 共用方法
     * 1. 根据imageurl的目录 展示目录下所有图片的缩略图
     * 返回值是数组类型,每个对象的key 与 uploadImage 的返回key相同, 前端处理也是类似 uploadImage.action
     *
     * @param imageurl
     * @return Array[{code:"",datetimeFileName:"",encodedAbspath:""}, {}]
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:query", "contract:query", "finance:query"}, logical = Logical.OR)
    @RequestMapping(value = "/showImgByDir", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showImgByDirAction(@RequestParam("imageurl") String imageurl) throws Exception {
        String decodedImageurl = URLDecoder.decode(imageurl, "GBK");
        Map<String, Object> map = new HashMap<String, Object>();
        map = FileLoad.getFilesInfoByimageurl(decodedImageurl);
        return map;
    }

   // @RequiresPermissions(value = {"voucher:create"}, logical = Logical.OR)
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFileAction(@RequestParam("file_data") MultipartFile multipartFile, @RequestParam("attachurl") String attachurl) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (multipartFile != null) {
            // 上传文件
            FileLoad.upload3(multipartFile, attachurl, map);

            // 写入数据库imagenames字段  20190715105510_1_jpg
            String datetimeFileName = (String) map.get("datetimeFileName");
            //根据imageurl解析出凭单的基本信息year month number company
            String decodedImageurl = URLDecoder.decode(attachurl, "GBK");
            Map<String, String> cfymn = new HashMap<String, String>();
            getCfymnByImageurl(decodedImageurl, cfymn);
            String company = cfymn.get("company");
            int year = Integer.parseInt(cfymn.get("year"));
            int month = Integer.parseInt(cfymn.get("month"));
            int number = Integer.parseInt(cfymn.get("number"));
            // 查询之前的imagenames字段内容
            String oldAttachnames = voucherService.getAttachnamesByCYMN(company, year, month, number);
            // 更新imagenames字段
            String newImagenames = oldAttachnames.equals("") ? datetimeFileName : oldAttachnames + "," + datetimeFileName;
            voucherService.addAttachByInfo(newImagenames, company, year, month, number);
        }
        return map;
    }

    /**
     * 1. 点击超链接 下载attach img 通用方法
     *
     * @param request  请求
     * @param response 响应
     * @param abspath  G:\\IPFOV\\1\新建文本.txt
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:query"}, logical = Logical.OR)
    @RequestMapping(value = "/downloadFileImg.action", method = RequestMethod.GET)
    public void downloadFileImgAction(HttpServletRequest request, HttpServletResponse response, String abspath) throws Exception {
        FileLoad.downLoadFileImg(request, response, abspath);
    }


    /**
     * 1.查询-上传-录入 凭单/合同/财务资料 后的下载文档
     *
     * @param request          请求
     * @param response         响应
     * @param attachurl        文档路径
     * @param datetimeFilename
     * @throws Exception
     */
   // @RequiresPermissions(value = {"voucher:query", "contract:query", "finance:query"}, logical = Logical.OR)
    @RequestMapping(value = "/downloadFileByAttachurlDatetimeFilename.action", method = RequestMethod.GET)
    public void downloadFileByAttachurlDatetimeFilenameAction(HttpServletRequest request, HttpServletResponse response,
                                                              @RequestParam("attachurl") String attachurl, @RequestParam("datetimeFilename") String datetimeFilename) throws Exception {
        String abspath = FileLoad.getFileRoot() + URLDecoder.decode(attachurl, "GBK") + datetimeFilename;
        FileLoad.downLoadFileImg(request, response, abspath);
    }


    private void getCfymnByImageurl(String decodedImageurl, Map<String, String> cfymn) {
        String company, function;
        String osDeli = FileLoad.getFileDelimeterByOS();
        if (decodedImageurl.substring(0, decodedImageurl.indexOf("_")).equals("xxzx"))
            company = "山东黄河信息中心";
        else
            company = "山东和信成信息技术中心";
        function = decodedImageurl.substring(decodedImageurl.indexOf("_") + 1, decodedImageurl.indexOf(osDeli));
        String date = decodedImageurl.substring(decodedImageurl.indexOf(osDeli) + 1, decodedImageurl.indexOf(osDeli, decodedImageurl.indexOf(osDeli) + 1));
        String year = date.substring(0, date.indexOf("-"));
        String month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
        String number = date.substring(date.lastIndexOf("-") + 1);
        cfymn.put("company", company);
        cfymn.put("year", year);
        cfymn.put("month", month);
        cfymn.put("number", number);
        cfymn.put("function", function);
    }

    /**
     * 1. 删除对应目录下的image和attach目录
     * 被 delV 调用
     *
     * @return
     */
    private int delVFileByCYMN(String imageurl, String attachurl) throws Exception {
        imageurl = URLDecoder.decode(imageurl, "GBK");
        attachurl = URLDecoder.decode(attachurl, "GBK");
        boolean codeImg = FileLoad.delFileByURL(imageurl);
        boolean codeAttach = FileLoad.delFileByURL(attachurl);
        if (codeImg == true && codeAttach == true)
            return 0;    //成功删除目录
        return -1;
    }


    /**
     * 1.根据年-月-编号 得到 此凭单所在目录的唯一标识
     * 被方法 addVAction-download-getVoucherImgNames 调用
     *
     * @param year   年份
     * @param month  月份
     * @param number 凭单编号
     * @return
     */
    private String getIdByYearMonthNumber(int year, int month, int number) {
        return year + "-" + month + "-" + number;
    }


    /**
     * 1. 根据参数返回 凭单的相对路径
     * 被方法 addVAction 调用
     *
     * @param companyCode xxzx or hxc
     * @param function    voucher or contract or finance
     * @param id          2019-1-1
     * @param type        img or attach
     * @return 相对路径        \xxzx_voucher\2019-1-4\img \xxzx_voucher\2019-1-4\attach
     */
    private String getRelUrl(String companyCode, final String function, String id, String type) throws Exception {
        String osDelimeter = FileLoad.getFileDelimeterByOS();
        return URLEncoder.encode(companyCode + "_" + function + osDelimeter + id + osDelimeter + type + osDelimeter, "UTF-8");
    }


}



