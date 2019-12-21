package com.forte.demo.anno;

import com.forte.demo.emun.FunEnum;

import java.lang.annotation.*;

/**
 * 权限校验注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Check {
    FunEnum type();
    int cost() default 0;
}
