package com.yuier.yuni.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OneBotEventDispatcher
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/11 22:45
 * @description: 注解，加在 OneBot 上报事件处理类上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneBotEventDispatcher {
    String value();
}
