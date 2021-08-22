package com.alibeta.easydict.factory.impl;

import com.alibeta.easydict.factory.IOutParamGetter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huojg
 */
public class OutParamGetterImpl extends ParamGetterImpl implements IOutParamGetter {

    @Override
    public boolean isEntityList(Method method) {
        Type type = method.getAnnotatedReturnType().getType();
        return false;
    }

    @Override
    public Class getEntityClass(Method method) throws ClassNotFoundException {
        Type type = method.getAnnotatedReturnType().getType();
        Class clazz = Class.forName(type.getTypeName());
        return clazz;
    }

    @Override
    public Class getEntityListClass(Method method) throws ClassNotFoundException {
        Type type = method.getAnnotatedReturnType().getType();
        Type[] typeNames = ((ParameterizedType) type).getActualTypeArguments();
        Class clazz = Class.forName(typeNames[0].getTypeName());
        return clazz;
    }

}
