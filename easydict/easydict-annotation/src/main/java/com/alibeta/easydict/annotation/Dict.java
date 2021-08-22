package com.alibeta.easydict.annotation;

import java.lang.annotation.*;

/**
 * 字典注解
 * 标注在实体类中需要进行字典替换的字段上
 *
 * @author huojg
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {

    /**
     * 字典表名
     */
    String dictTable();

    /**
     * 字典表中字典类型
     */
    String dictTypeValue();

    /**
     * 字典表中字典类型对应的字段名
     */
    String dictType() default "type";

    /**
     * 字典表中字典编码对应的字段名
     */
    String dictCode() default "code";

    /**
     * 字典表中字典文本对应的字段名
     */
    String dictText() default "text";

    /**
     * 实体类中用来存放字典文本的字段后缀
     */
    String dictTextSuffix() default "Text";

}
