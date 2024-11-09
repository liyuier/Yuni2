package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.enums.NoticeEventEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: OneBotNoticeEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.annotation
 * @Date 2024/4/10 23:48
 * @description: 注解，加在 OneBot 通知事件实体类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneBotNoticeEventEntity {
    NoticeEventEnum noticeType();
}
