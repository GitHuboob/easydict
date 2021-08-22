package com.alibeta.easydict.factory;

import java.util.List;

/**
 * @author huojg
 */
public interface IParamGetter {

    boolean isEntityList(Object object);

    Object getEntity(Object object);

    List<Object> getEntityList(Object object);

}
