package com.alibeta.easydict.annotation;

import java.lang.annotation.*;

/**
 * 字典替换注解
 * 标注在需要进行字典替换的方法上，默认通过字典code查询字典text
 *
 * @author huojg
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictReplace {

    enum DIRECTION {CODE2TEXT, TEXT2CODE}

    /**
     * 替换方向
     */
    DIRECTION direction() default DIRECTION.CODE2TEXT;

}
