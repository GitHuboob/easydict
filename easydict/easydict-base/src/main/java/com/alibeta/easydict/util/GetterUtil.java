package com.alibeta.easydict.util;

import com.alibeta.easydict.annotation.DictReplace;
import com.alibeta.easydict.factory.IFactory;
import com.alibeta.easydict.factory.IInParamGetter;
import com.alibeta.easydict.factory.IOutParamGetter;
import com.alibeta.easydict.factory.IParamGetter;
import com.alibeta.easydict.factory.impl.FactoryImpl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 获取出参、入参的Class
 *
 * @author huojg
 */
public class GetterUtil {

    private static IFactory iClassFactory = new FactoryImpl();
    private static IParamGetter iParamGetter = iClassFactory.createIParamGetter();
    private static IInParamGetter iInParamGetter = iClassFactory.createIInParamGetter();
    private static IOutParamGetter iOutParamGetter = iClassFactory.createIOutParamGetter();

    public static Class getClass(Method method) throws ClassNotFoundException {
        Class clazz = null;
        Type[] types = method.getGenericParameterTypes();
        Type type = method.getAnnotatedReturnType().getType();
        DictReplace.DIRECTION direction = method.getAnnotation(DictReplace.class).direction();
        switch (direction) {
            case CODE2TEXT:
                clazz = type instanceof ParameterizedType ? iOutParamGetter.getEntityListClass(method) : iOutParamGetter.getEntityClass(method);
                break;
            case TEXT2CODE:
                clazz = types[0] instanceof ParameterizedType ? iInParamGetter.getEntityListClass(method) : iInParamGetter.getEntityClass(method);
                break;
        }
        return clazz;
    }

    public static boolean isEntityList(Object object) {
        return iParamGetter.isEntityList(object);
    }

    public static Object getEntity(Object object) {
        return iParamGetter.getEntity(object);
    }

    public static List<Object> getEntityList(Object object) {
        return iParamGetter.getEntityList(object);
    }

}
