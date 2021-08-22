package com.alibeta.easydict.util;

import com.alibeta.easydict.cache.CacheFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 字典翻译类
 *
 * @author huojg
 */
public class TranslateUtil {

    /**
     * 翻译字典文本
     */
    public static String translateDictValueByKeyValue(String table, String typeCol, String codeCol, String textCol, String dictTypeValue, String value) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        // 缓存数据库字典表所有数据
        List<Map<Object, Object>> dictData = CacheFactory.getDictData(table, typeCol, codeCol, textCol);
        // 遍历字典表数据
        Map<Object, Object> dictMap = new HashMap<Object, Object>();
        for (Map<Object, Object> map : dictData) {
            String typeColValue = String.valueOf(map.get(typeCol));
            String codeColValue = String.valueOf(map.get(codeCol));
            String textColValue = String.valueOf(map.get(textCol));
            if (dictTypeValue.equals(typeColValue)) {
                dictMap.put(codeColValue, textColValue);
                dictMap.put(textColValue, codeColValue);
            }
        }
        if (null != dictMap && dictMap.containsKey(value)) {
            return dictMap.get(value).toString();
        }
        return null;
    }

    /**
     * 翻译字典文本
     */
    public static String translateDictValueByKeyLable(String table, String typeCol, String codeCol, String textCol, String dictTypeValue, String label) {
        if (StringUtils.isEmpty(label)) {
            return null;
        }
        // 缓存数据库字典表所有数据
        List<Map<Object, Object>> dictData = CacheFactory.getDictData(table, typeCol, codeCol, textCol);
        // 遍历字典表数据
        Map<Object, Object> dictMap = new HashMap<Object, Object>();
        for (Map<Object, Object> map : dictData) {
            String typeColValue = String.valueOf(map.get(typeCol));
            String codeColValue = String.valueOf(map.get(codeCol));
            String textColValue = String.valueOf(map.get(textCol));
            if (dictTypeValue.equals(typeColValue)) {
                dictMap.put(codeColValue, textColValue);
                dictMap.put(textColValue, codeColValue);
            }
        }
        if (null != dictMap && dictMap.containsKey(label)) {
            return dictMap.get(label).toString();
        }
        return null;
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<Field>();
        while (null != clazz) {
            fieldList.addAll(new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

}
