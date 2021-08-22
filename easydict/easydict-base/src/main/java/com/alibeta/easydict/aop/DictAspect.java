package com.alibeta.easydict.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibeta.easydict.annotation.Dict;
import com.alibeta.easydict.annotation.DictReplace;
import com.alibeta.easydict.util.GetterUtil;
import com.alibeta.easydict.util.JdbcUtil;
import com.alibeta.easydict.util.TranslateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huojg
 */
@Component
@EnableAspectJAutoProxy
@Aspect
public class DictAspect {

    private static Logger logger = Logger.getLogger(DictAspect.class);

    /**
     * 对 @DictLoader 进行切面
     */
    @Pointcut("@annotation(com.alibeta.easydict.annotation.DictLoader)")
    public void dictLoader() {
    }

    /**
     * 对 @DictLoader 切面具体实现
     *
     * @param pjp
     * @return
     */
    @Around("dictLoader()")
    public Object dictLoader(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            new JdbcUtil(method);
            result = pjp.proceed();
        } catch (Throwable t) {
            logger.error("字典切面类加载报错！", t);
            throw new RuntimeException("字典切面类加载报错！");
        } finally {
            return result;
        }
    }

    /**
     * 对 @DictReplace 进行切面
     */
    @Pointcut("@annotation(com.alibeta.easydict.annotation.DictReplace)")
    public void dictReplace() {
    }

    /**
     * 对 @DictReplace 切面具体实现
     *
     * @param pjp
     * @return
     */
    @Around("dictReplace()")
    public Object dictReplace(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            // 获取实体类class
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            Class clazz = GetterUtil.getClass(method);
            // 获取方法上的注解信息，判断正向还是反向替换
            DictReplace.DIRECTION direction = method.getAnnotation(DictReplace.class).direction();
            // 插入方法，对入参进行反向替换，即text->code
            if (DictReplace.DIRECTION.TEXT2CODE.equals(direction)) {
                parseDictText(pjp.getArgs()[0], direction, clazz);
            }
            result = pjp.proceed();
            // 查询方法，对出参进行正向替换，即code->text
            if (DictReplace.DIRECTION.CODE2TEXT.equals(direction)) {
                parseDictText(result, direction, clazz);
            }
        } catch (Throwable t) {
            logger.error("字典切面类解析报错！", t);
            throw new RuntimeException("字典切面类解析报错！");
        } finally {
            return result;
        }
    }

    /**
     * 对入参、出参进行字典解析
     *
     * @param result
     * @param clazz
     * @throws JsonProcessingException
     */
    private void parseDictText(Object result, DictReplace.DIRECTION direction, Class clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (GetterUtil.isEntityList(result)) {
            // 参数为列表<实体类>时
            List<Object> items = new ArrayList<Object>();
            List<Object> entityList = GetterUtil.getEntityList(result);
            for (Object object : entityList) {
                object = parseDictText(object, objectMapper, direction, clazz);
                items.add(object);
            }
            ((List) result).clear();
            ((List) result).addAll(items);
        } else {
            // 参数为实体类时
            Object object = GetterUtil.getEntity(result);
            object = parseDictText(object, objectMapper, direction, clazz);
            BeanUtils.copyProperties(object, result, clazz);
        }
    }

    /**
     * 对入参、出参进行字典替换
     *
     * @param object
     * @param objectMapper
     * @param direction
     * @param clazz
     * @return
     * @throws JsonProcessingException
     */
    Object parseDictText(Object object, ObjectMapper objectMapper, DictReplace.DIRECTION direction, Class clazz) throws JsonProcessingException {
        // 将object对象转为JSONObject
        String json = objectMapper.writeValueAsString(object);
        JSONObject item = JSONObject.parseObject(json);
        // 判断实体类是否存在字典注解，存在的话进行字典替换
        for (Field field : TranslateUtil.getAllFields(object)) {
            if (null == field.getAnnotation(Dict.class)) {
                continue;
            }
            // 数据库表名和字段名
            String table = field.getAnnotation(Dict.class).dictTable();
            String typeCol = field.getAnnotation(Dict.class).dictType();
            String codeCol = field.getAnnotation(Dict.class).dictCode();
            String textCol = field.getAnnotation(Dict.class).dictText();
            // 业务相关数据
            String dictTypeValue = field.getAnnotation(Dict.class).dictTypeValue();
            String dictTextSuffix = field.getAnnotation(Dict.class).dictTextSuffix();
            // 对字典值替换
            if (DictReplace.DIRECTION.TEXT2CODE.equals(direction)) {
                String retVal = String.valueOf(item.get(field.getName() + dictTextSuffix));
                String textValue = TranslateUtil.translateDictValueByKeyValue(table, typeCol, codeCol, textCol, dictTypeValue, retVal);
                item.put(field.getName(), textValue);
            } else {
                String retVal = String.valueOf(item.get(field.getName()));
                String textValue = TranslateUtil.translateDictValueByKeyLable(table, typeCol, codeCol, textCol, dictTypeValue, retVal);
                item.put(field.getName() + dictTextSuffix, textValue);
            }
        }
        object = objectMapper.convertValue(item, clazz);
        return object;
    }

}
