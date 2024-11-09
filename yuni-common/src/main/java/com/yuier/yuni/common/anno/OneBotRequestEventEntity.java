package com.yuier.yuni.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OneBotRequestEventEntity
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/11 1:30
 * @description: 注解，加在 OneBot 请求事件实体类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneBotRequestEventEntity {
    String requestType();
}
