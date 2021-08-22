package com.alibeta.easydict.annotation;

import java.lang.annotation.*;

/**
 * 字典加载注解
 * 标注在启动类的方法上，只支持mysql数据库
 *
 * @author huojg
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictLoader {

    /**
     * 数据库驱动类
     */
    String className();

    /**
     * 数据库连接地址
     */
    String url();

    /**
     * 数据库用户
     */
    String username();

    /**
     * 数据库密码
     */
    String password();

    /**
     * 配置文件路径
     */
    String[] locations() default {};

}
