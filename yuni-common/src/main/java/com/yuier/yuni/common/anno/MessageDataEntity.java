package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.enums.MessageDataEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: MessageData
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/14 4:29
 * @description: 注解，加在消息段数据实体类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageDataEntity {
    MessageDataEnum dataType();
}
