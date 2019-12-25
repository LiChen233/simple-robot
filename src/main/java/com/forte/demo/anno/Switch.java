package com.forte.demo.anno;

import com.forte.demo.emun.FunEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 群功能开关的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Switch {
    //功能
    FunEnum[] types();
    //启用or禁用
    int status();
    //需要 管理员权限：0 购买者权限：1
    int admin() default 0;
    //功能名字
    String name();
}
