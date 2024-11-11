package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.domain.event.OneBotEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: EventHandler
 * @Author yuier
 * @Package com.yuier.yuni.common.anno
 * @Date 2024/11/11 22:35
 * @description: OneBot 上报事件处理器
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    // 上报消息类型
    Class<? extends OneBotEvent> eventType();
}
