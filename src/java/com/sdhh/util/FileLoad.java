package com.sdhh.util;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传 下载
 *
 * @author WangYing
 * @time 2019年5月7日下午4:00:59
 */

public class FileLoad {

    private static Logger logger = Logger.getLogger(FileLoad.class);

    /**
     * 给定绝对路径返回目录下的所有文件名
     *
     * @param absDirPath
     * @return
     * @throws Exception
     */
    private static ArrayList<String> getFileNameByDir(String absDirPath) throws Exception {
        ArrayList<String> fileNames = new ArrayList<String>();
        File dir = new File(absDirPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNames.add(files[i].getName());
            }
        }
        return fileNames;
    }

    /**
     * 获取存储文件的绝对路径
     *
     * @param company  例如对应绝对路径中的xxzx
     * @param function 例如对应绝对路径中的 voucher
     * @param id       例如对应绝对路径中的2019-1-100
     * @param type     例如对应绝对路径中的img
     * @return G:\IPFOV\xxzx_voucher\2019-1-100\img\
     * G:\IPFOV\hxc_voucher\2019-1-100\img\
     */
    private static String getAbsPath(String company, String function, String id, String type) throws Exception {
//		读取配置文件 根据操作系统判断文件在本地存放的根目录fileRoot
        String fileRoot = getFileRoot();
        String osDelimeter = getFileDelimeterByOS();

//		保存图片或文档的绝对路径absPath
        String absPath = fileRoot + company + "_" + function + osDelimeter + id + osDelimeter + type + osDelimeter;
        logger.debug("<---------->" + "保存图片或附件的绝对路径是:-----" + absPath);
        return absPath;
    }

    /**
     * 读取配置文[fileServer.properties]件  获取当前操作系统下 保存图片和附件的根目录
     *
     * @return
     */
    public static String getFileRoot() {
        try {
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") == 0) {
                return ResourcesUtil.getValue("fileServer", "windows.fileRoot");
            } else {
                return ResourcesUtil.getValue("fileServer", "linux.fileRoot");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResourcesUtil.getValue("fileServer", "windows.fileRoot");
    }

    /**
     * 根据相对路径返回保存图片和附件的绝对路径
     *
     * @param relUrl xxzx_voucher\2019-1-1\img
     * @return 如果是windows操作系统则返回 G:\IPFOV\xxzx_voucher\2019-1-1\img(自动根据配置文件返回绝对路径)
     */
    private static String getAbsPath(String relUrl) {
        return getFileRoot() + relUrl;
    }

    /**
     * 根据操作系统返回文件分隔符
     *
     * @return
     */
    public static String getFileDelimeterByOS() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("windows") == 0) {
                return "\\";
            }
        } catch (Exception e) {
        }
        return "//";
    }

    /**
     * 根据目录下载此目录下的所有文件
     * 根据参数function-id-type[参数举例voucher/2019-1-1/img]下载此目录下的所有文件
     *
     * @param req
     * @param res
     * @param company
     * @param function
     * @param id
     * @param type
     * @throws Exception
     */
    public static void downloadFileByDir(HttpServletRequest req, HttpServletResponse res,
                                         String company, String function, String id, String type) throws Exception {
//		 遍历得到此目录下的所有文件名
        ArrayList<String> fileNames = getFileNameByDir(getAbsPath(company, function, id, type));
        for (int i = 0; i < fileNames.size(); i++) {
            downloadFileByName(req, res, company, function, id, type, fileNames.get(i));
        }
    }


    /**
     * 根据参数function-id-type-fileName[参数举例山东黄河信息中心-voucher/2019-1-1/img/1.jpg]下载此目录下对应名称的文件
     *
     * @param request
     * @param response
     * @param function 功能类别:值是 voucher或contract或finance
     * @param id       [凭单/合同/财务资料]目录的唯一标识：例如凭单目录的唯一标识2019-1-100
     * @param type     文件类型是:img或attach
     * @param fileName 文件名
     * @throws Exception
     */
    public static void downloadFileByName(HttpServletRequest request, HttpServletResponse response,
                                          String company, String function, String id, String type, String fileName) throws Exception {

        //String pathTmp = request.getServletContext().getRealPath("/WEB-INF/"+uploadPath);
//		通过文件名遍历根目录下,找到对应的文件
        String absPath = getAbsPath(company, function, id, type);
        if (absPath != null) {
            File file = new File(absPath + fileName);
            if (!file.exists()) {
                request.setAttribute("message", "文件不存在,请刷新重试!");
                logger.debug("<---------->" + "文件不存在：" + fileName);
                request.getRequestDispatcher("/fileTest1.jsp").forward(request, response);
                return;
            }
//			把fileName转换为utf-8格式的字节数组,在用ISO格式编码成字符串String格式
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1").replaceAll(" ", "_"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            byte[] buff = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(absPath + fileName));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            int i;
            while ((i = bis.read(buff)) != -1) {
                bos.write(buff, 0, i);
            }
            bis.close();
            bos.close();
        }
    }

    /**
     * 删除文件
     *
     * @param company  单位
     * @param function voucher或者contract或者finance
     * @param id       凭单或合同或financed唯一编号
     * @param type     img或者attach
     */
    public static boolean delFile(String company, final String function, String id, String type) {
        try {
            File folder = new File(getAbsPath(company, function, id, type));
            if (folder.exists())
                return folder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据相对路径删除目录
     *
     * @param relUrl xxzx_voucher\2019-1-1\img
     * @return
     */
    public static boolean delFileByURL(String relUrl) {
        File folder = new File(getAbsPath(relUrl));
        if (folder.isDirectory())
            return deleteDir(folder);
        return true;
    }

    /**
     * 删除绝对路径下的所有文件
     *
     * @param folder
     * @return
     */
    public static boolean deleteDir(File folder) {
        File[] allFiles = folder.listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            File f = allFiles[i];
            if (f.isDirectory())
                deleteDir(f);
            else
                f.getAbsoluteFile().delete();//这里用f.deleter(); //有时候删除不了目录
        }
        return true;
    }

    /**
     * 根据参数,返回此目录下的所有文件名称,用逗号分隔
     *
     * @param company  山东黄河信息中心
     * @param function 2019
     * @param id       1
     * @param type     img or attach
     * @return 1.jpg, 2.jpg, 3.jpg
     */
    public static String getFileNamesByUrl(String company, final String function, String id, String type) throws Exception {
        StringBuffer fileNames = new StringBuffer();
//		absPath===G:\IPFOV\hxc_voucher\2019-1-100\img
        String absPath = getAbsPath(company, function, id, type);
        File folder = new File(absPath);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                fileNames.append(files[i].getName());
                if (i != 0)
                    fileNames.append(",");
            }
        }
        return fileNames.toString();
    }


    /**
     * @param file     通过表单传的参数 类型是CommonsMultipartFile
     * @param company
     * @param function voucher or contract of finance
     * @param id       2019-1-1
     * @param type     img or attach
     * @param fileName 1.jpg
     * @return
     * @throws Exception
     */
    public static int uploadFileByName(CommonsMultipartFile file, String company, final String function, String id, final String type, String fileName) throws Exception {
        String absPath = getAbsPath(company, function, id, type) + fileName;
        File fileByAbsPath = new File(absPath);
        file.transferTo(fileByAbsPath);
        return 0;
    }


    public static void uploadImage(MultipartFile multipartFile, String imageurl, Map<String, String> map) throws Exception {
        String decodedFilepath = URLDecoder.decode(imageurl, "GBK");
        // decodedFilepath: xxzx_voucher\\
        String filename = multipartFile.getOriginalFilename();
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String datetimeFileName = datetime + "-" + filename;
        String parentPath = FileLoad.getFileRoot() + decodedFilepath;
        File newFilepath = new File(parentPath);
        if (!newFilepath.exists()) {
            newFilepath.mkdirs();
        }
        String abspath = parentPath + datetimeFileName;
        File newFile = new File(abspath);
        logger.debug("<---------->" + multipartFile.getOriginalFilename() + "上传成功");
        multipartFile.transferTo(newFile);
//		返回data.encodedAbspath 		G%3A%4CIPFOV%5C1%5C新建文本.txt
        //data.datetimeFileName		20190715105510_1_jpg
        map.put("code", "1");
        String encodedAbspath = URLEncoder.encode(abspath, "UTF-8");
        map.put("encodedAbspath", encodedAbspath);
        // datetimeFileName 作为 前端img标签的id标识
        map.put("datetimeFileName", datetimeFileName);
    }

    /**
     * 1. 根据单个图片的绝对路径 abspath 下载此图片
     *
     * @param request
     * @param response
     * @param abspath  单个图片的绝对路径
     */
    public static void showImg(HttpServletRequest request, HttpServletResponse response, String abspath) {
        String pathName2 = abspath;
        response.setContentType("application/octet-streamcharset=UTF-8");
        File imgFile = new File(pathName2);
        FileInputStream fin = null;
        ServletOutputStream output = null;
        try {
            output = response.getOutputStream();
            fin = new FileInputStream(imgFile);
            byte[] arr = new byte[1024 * 10];
            int n;
            while ((n = fin.read(arr)) != -1) {
                output.write(arr, 0, n);
            }
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. 文件上传
     *
     * @param multipartFile
     * @param attachurl     xxzx_voucher%5C2019-1-1%5Cattach%5C
     * @param map
     * @throws Exception
     */
    public static void upload3(MultipartFile multipartFile, String attachurl, Map<String, Object> map) throws Exception {
        String decodedFilepath = URLDecoder.decode(attachurl, "GBK");
        String filename = multipartFile.getOriginalFilename();
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String datetimeFileName = datetime + "-" + filename;
        String parentPath = FileLoad.getFileRoot() + decodedFilepath;
        File newFilepath = new File(parentPath);
        if (!newFilepath.exists()) {
            newFilepath.mkdirs();
        }
        String abspath = parentPath + datetimeFileName;
        File newFile = new File(abspath);
        logger.debug("<---------->" + multipartFile.getOriginalFilename() + "上传成功");
        multipartFile.transferTo(newFile);
        String encodedAbspath = URLEncoder.encode(abspath, "UTF-8");
        map.put("encodedAbspath", encodedAbspath);
        // datetimeFileName 作为 前端img标签的id标识
        map.put("datetimeFileName", datetimeFileName);
        map.put("code", "1");
    }

    /**
     * 显示 此凭单的所有文件附件
     *
     * @param attachurl
     * @param datetimeFileName
     * @return
     */
    public Map<String, Object> getAllAttach(String attachurl, String datetimeFileName) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String decodedFilepath = FileLoad.getFileRoot() + URLDecoder.decode(attachurl, "GBK");
        String parentPath = FileLoad.getFileRoot() + decodedFilepath;
        File newFilepath = new File(parentPath);
//		读取目录path下的所有文件的绝对路径名称 前端点击a链接进行下载
        File files[] = newFilepath.listFiles();
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < files.length; i++) {
//			前端要显示的已经上传的文件名，这里需要encode一下，到前端再decode，才能正确显示带文件路径分隔符\
            HashMap<String, String> name = new HashMap<String, String>();
            String encodedAbspath = URLEncoder.encode(files[i].getPath(), "UTF-8");
            name.put("encodedAbspath", encodedAbspath);
            // datetimeFileName 作为 前端img标签的id标识
            name.put("datetimeFileName", datetimeFileName);
            data.add(name);
        }
        map.put("data", data);
        return map;
    }

    /**
     * 1.
     * 给定文件所在磁盘的绝对路径 下载文件
     *
     * @param request
     * @param response
     * @param abspath  文件所在磁盘的绝对路径  G:\\IPFOV\\1\新建文本.txt
     * @throws Exception
     */
    public static void downLoadFileImg(HttpServletRequest request, HttpServletResponse response, String abspath) throws Exception {
        if ("".equals(abspath) || abspath == null || "null".equals(abspath))
            return;
        String[] pathArr = abspath.split("\\\\");    //字符串中的\要转义
        String fileName = pathArr[pathArr.length - 1];
        File file = new File(abspath);
        if (!file.exists()) {
            request.setAttribute("message", "文件不存在,请刷新重试!");
            logger.debug("<---------->" + "fail====downLoadFileImg=====文件不存在：" + abspath);
            //request.getRequestDispatcher("/voucher/voucherIndex.jsp").forward(request, response);
            return;
        }
//				把fileName转换为utf-8格式的字节数组,在用ISO格式编码成字符串String格式
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1").replaceAll(" ", "_"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        byte[] buff = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(abspath));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        int i;
        while ((i = bis.read(buff)) != -1) {
            bos.write(buff, 0, i);
        }
        bis.close();
        bos.close();
    }

    /**
     * 1.
     * 删除文件 返回此文件所在目录下其他文件绝对路径名
     *
     * @param abspath 要删除的参数是   G:\\IPFOV\\1\新建文本.txt
     * @throws Exception
     * @return ['G:\\IPFOV\\1\文件1.txt','G:\\IPFOV\\1\文件2.txt']
     */
    public static String[] deleteByAbspath(String abspath) throws Exception {
        File deleteFile = new File(abspath);
        if (!deleteFile.exists())
            return new String[0];
        ArrayList<String> allFiles = new ArrayList<String>();
        boolean flag = deleteFile.delete();
        if (flag == false)
            return new String[0];
        File[] all = deleteFile.getParentFile().listFiles();
        for (int i = 0; i < all.length; i++) {
            allFiles.add(all[i].getAbsolutePath());
        }
        return (String[]) allFiles.toArray(new String[allFiles.size()]);
    }


    /**
     * 1. 根据imageurl读出所有文件的 datetimeFileName,encodedAbspath
     *
     * @param imageurl
     * @return
     */
    public static Map<String, Object> getFilesInfoByimageurl(String imageurl) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ArrayList<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        File absDirFile = new File(FileLoad.getFileRoot() + imageurl);
        if (absDirFile.isDirectory()) {
            File[] files = absDirFile.listFiles();
            //如果没有文件则返回前端提示
            if (files.length == 0) {
                map.put("code", "0");
                return map;
            }
            map.put("code", "1");
            // 遍历目录下的文件
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                String datetimeFileName = f.getName();
                String encodedAbspath = URLEncoder.encode(f.getAbsolutePath(), "UTF-8");
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("datetimeFileName", datetimeFileName);
                obj.put("encodedAbspath", encodedAbspath);
                filesInfo.add(obj);
            }
        }
        map.put("data", filesInfo);
        return map;
    }

    /**
     * 1.根据imageurl 或 attachurl 创建img 和 attach 目录
     * addV 调用
     *
     * @param imageurl 编码了的url
     */
    public static void CrtDir(String imageurl) throws Exception {
        File absDirFile = new File(FileLoad.getFileRoot() + URLDecoder.decode(imageurl, "GBK"));
        if (!absDirFile.exists())
            absDirFile.mkdirs();
    }


}
