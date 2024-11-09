package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.enums.MetaEventEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OneBotMetaEventEntity
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/11 1:33
 * @description: 注解，加在 OneBot 元事件实体类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneBotMetaEventEntity {
    MetaEventEnum metaEventType();
}
