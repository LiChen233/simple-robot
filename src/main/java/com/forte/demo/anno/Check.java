package com.forte.demo.anno;

import java.lang.annotation.*;

/**
 * 权限校验注解
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Check {
}
