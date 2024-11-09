package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.enums.OneBotEventEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OneBotEventHandler
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/11 22:45
 * @description: 注解，加在 OneBot 上报事件处理类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneBotEventHandler {
    OneBotEventEnum eventType();
}
