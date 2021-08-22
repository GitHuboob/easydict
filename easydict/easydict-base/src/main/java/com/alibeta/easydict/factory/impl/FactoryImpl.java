package com.alibeta.easydict.factory.impl;

import com.alibeta.easydict.factory.IFactory;
import com.alibeta.easydict.factory.IInParamGetter;
import com.alibeta.easydict.factory.IOutParamGetter;
import com.alibeta.easydict.factory.IParamGetter;

/**
 * @author huojg
 */
public class FactoryImpl implements IFactory {

    @Override
    public IParamGetter createIParamGetter() {
        return new ParamGetterImpl();
    }

    @Override
    public IInParamGetter createIInParamGetter() {
        return new InParamGetterImpl();
    }

    @Override
    public IOutParamGetter createIOutParamGetter() {
        return new OutParamGetterImpl();
    }

}
