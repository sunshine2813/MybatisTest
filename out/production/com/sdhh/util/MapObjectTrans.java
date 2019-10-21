package com.sdhh.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangYing
 * @time 2019年3月29日下午4:37:36
 */

public final class MapObjectTrans {

    /**
     * 把对象返回为一个map
     *
     * @param obj User对象
     * @throws Exception
     * @return 返回值格式为map {"name":"alice","id":"1"}
     */
    public static Map<String, Object> ObjectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;
        Map<String, Object> map = new HashMap<String, Object>();
//		获取对象的所有属性描述对象
        BeanInfo beaninfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beaninfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
//			获取属性的key
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
//			获取对应key的getter方法
            Method getter = property.getReadMethod();
//			调用getter方法获取对应的value
            Object value = getter != null ? getter.invoke(obj) : null;
//			放入map中
            map.put(key, value);
        }
        return map;
    }

    /**
     * 把map返回对应的一个对象
     *
     * @param map       参数格式类似{"name":"alice","id":"1"}
     * @param beanClass 参数类似 User.class
     * @return 返回一个User对象
     * @throws Exception
     */
    public static Object MapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] PropertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : PropertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
//				从map里根据属性名（即key）取出value,作为 调用obj对象setter方法 的第二个参数
                setter.invoke(obj, map.get(property.getName()));
            }
        }
        return obj;

    }


}
