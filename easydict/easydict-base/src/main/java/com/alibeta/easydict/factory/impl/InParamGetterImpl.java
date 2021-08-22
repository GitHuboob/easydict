package com.alibeta.easydict.factory.impl;

import com.alibeta.easydict.factory.IInParamGetter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huojg
 */
public class InParamGetterImpl extends ParamGetterImpl implements IInParamGetter {

    @Override
    public boolean isEntityList(Method method) {
        return false;
    }

    @Override
    public Class getEntityClass(Method method) throws ClassNotFoundException {
        Type[] types = method.getGenericParameterTypes();
        Class clazz = Class.forName(types[0].getTypeName());
        return clazz;
    }

    @Override
    public Class getEntityListClass(Method method) throws ClassNotFoundException {
        Type[] types = method.getGenericParameterTypes();
        Type[] typeNames = ((ParameterizedType) types[0]).getActualTypeArguments();
        Class clazz = Class.forName(typeNames[0].getTypeName());
        return clazz;
    }

}
