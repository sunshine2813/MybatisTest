package com.sdhh.util;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author WangYing
 * @time 2019年7月6日上午11:05:44
 */

public class ResourcesUtil implements Serializable {

    private static final long serialVersionUID = -7657898714983901418L;
    private static Logger logger = Logger.getLogger(ResourcesUtil.class);

    /**
     * 系统语言环境，默认为中文zh
     */
    public static final String LANGUAGE = "zh";

    /**
     * 系统国家环境，默认为中国CN
     */
    public static final String COUNTRY = "CN";

    private static Locale getLocale() {
        Locale locale = new Locale(LANGUAGE, COUNTRY);
        return locale;
    }

    /**
     * 根据语言、国家、资源文件名和key名字获取资源文件值
     *
     * @param baseName 资源文件名
     * @param section  key名字
     * @return value
     */
    private static String getProperties(String baseName, String section) {
        String retValue = "";
        try {
            Locale locale = getLocale();
            ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
            retValue = (String) rb.getObject(section);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO 添加处理
        }
        return retValue;
    }

    /**
     * 通过key从资源文件读取内容
     *
     * @param fileName 资源文件名(去掉后缀properties)
     * @param key
     * @return value
     */
    public static String getValue(String fileName, String key) {
        String value = getProperties(fileName, key);
        return value;
    }


    /**
     * 默认读取classpath下的properties配置文件
     * 没有配置classpath 则ResourceBundle.getBundle的参数必须指定路径 如com.sdhh.文件名(不用加后缀properties)
     *
     * @param baseName
     * @return
     */
    public static List<String> getKeyList(String baseName) {
        Locale locale = getLocale();
        ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);

        List<String> reslist = new ArrayList<String>();

        Set<String> keyset = rb.keySet();
        for (String s : keyset) {
            reslist.add(s);
        }
        return reslist;
    }

    /**
     * 通过key从资源文件读取内容，并格式化
     *
     * @param fileName 资源文件名
     * @param key      索引
     * @param objs     格式化参数
     * @return 格式化后的内容
     */
    public static String getValue(String fileName, String key, Object[] objs) {
        String pattern = getValue(fileName, key);
        String value = MessageFormat.format(pattern, objs);
        return value;
    }

    public static void main(String[] args) {
        String fileName = "com.sdhh.grammer.filePath";
        List<String> keyList = getKeyList(fileName);
        for (int i = 0; i < keyList.size(); i++) {
            logger.debug("<---------->" + getValue(fileName, keyList.get(i)));
        }
        //System.out.println(getValue("resources.messages", "101",new Object[]{100,200}));


        //根据操作系统环境获取语言环境
        /*Locale locale = Locale.getDefault();
		System.out.println(locale.getCountry());//输出国家代码
		System.out.println(locale.getLanguage());//输出语言代码s
		
		//加载国际化资源（classpath下resources目录下的messages.properties，如果是中文环境会优先找messages_zh_CN.properties）
		ResourceBundle rb = ResourceBundle.getBundle("resources.messages", locale);
		String retValue = rb.getString("101");//101是messages.properties文件中的key
		System.out.println(retValue);
		
		//信息格式化，如果资源中有{}的参数则需要使用MessageFormat格式化，Object[]为传递的参数，数量根据资源文件中的{}个数决定
		String value = MessageFormat.format(retValue, new Object[]{100,200});
		System.out.println(value);
*/

    }
}

