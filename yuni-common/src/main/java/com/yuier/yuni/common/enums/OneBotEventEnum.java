package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: OneBotEventEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 18:56
 * @description: OneBot 实现上报事件类型枚举类
 */
@Getter
public enum OneBotEventEnum {

    MESSAGE("message", "消息事件"),
    NOTICE("notice", "通知事件"),
    REQUEST("request", "请求事件"),
    META("meta_event", "元事件"),
    MESSAGE_SENT("message_sent", "bot 自身发送消息事件");


    private final String eventType;
    private final String description;

    OneBotEventEnum(String eventType, String description) {
        this.eventType = eventType;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.eventType;
    }

}
