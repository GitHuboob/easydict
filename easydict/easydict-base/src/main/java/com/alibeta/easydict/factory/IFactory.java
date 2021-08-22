package com.alibeta.easydict.factory;

/**
 * @author huojg
 */
public interface IFactory {

    IParamGetter createIParamGetter();

    IInParamGetter createIInParamGetter();

    IOutParamGetter createIOutParamGetter();

}
