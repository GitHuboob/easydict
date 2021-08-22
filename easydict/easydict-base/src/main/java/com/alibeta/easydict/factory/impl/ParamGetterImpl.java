package com.alibeta.easydict.factory.impl;

import com.alibeta.easydict.factory.IParamGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huojg
 */
public class ParamGetterImpl implements IParamGetter {

    @Override
    public boolean isEntityList(Object object) {
        if (object instanceof List) {
            return true;
        }
        return false;
    }

    @Override
    public Object getEntity(Object object) {
        if (object instanceof List) {
            return ((List) object).get(0);
        }
        return object;
    }

    @Override
    public List<Object> getEntityList(Object object) {
        if (object instanceof List) {
            return ((List) object);
        }
        List<Object> list = new ArrayList<Object>();
        list.add(object);
        return list;
    }

}
