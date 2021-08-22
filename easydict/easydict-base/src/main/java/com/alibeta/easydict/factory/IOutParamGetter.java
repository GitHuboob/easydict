package com.alibeta.easydict.factory;

import java.lang.reflect.Method;

/**
 * @author huojg
 */
public interface IOutParamGetter extends IParamGetter {

    boolean isEntityList(Method method);

    Class getEntityClass(Method method) throws ClassNotFoundException;

    Class getEntityListClass(Method method) throws ClassNotFoundException;

}
