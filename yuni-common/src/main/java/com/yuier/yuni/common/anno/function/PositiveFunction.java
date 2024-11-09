package com.yuier.yuni.common.anno.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: PositiveFunction
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/15 22:07
 * @description: 注解，加在主动出发的功能插件上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveFunction {
}
