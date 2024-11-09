package com.yuier.yuni.common.anno.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OrderFunction
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/15 1:33
 * @description: 注解，加载指令触发的功能插件上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderCallFunction {
    String orderWord() default "";

}
