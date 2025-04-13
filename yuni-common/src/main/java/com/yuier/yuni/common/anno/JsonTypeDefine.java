package com.yuier.yuni.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: JsonTypeDefine
 * @Author yuier
 * @Package com.yuier.yuni.common.anno
 * @Date 2024/11/10 23:37
 * @description: 辅助注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonTypeDefine {
    String value() default "";
}
