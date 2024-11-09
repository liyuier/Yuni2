package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MsgTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 20:14
 * @description: 消息事件类型枚举类
 */
@Getter
public enum MessageTypeEnum {

    PRIVATE("private", "私聊消息"),
    GROUP("group", "群聊消息"),
    ALL("all", "全部消息类型"),
    NONE("none", "不监听消息，用于主动触发的插件");

    private final String msgType;
    private final String description;

    MessageTypeEnum(String msgType, String description) {
        this.msgType = msgType;
        this.description = description;
    }

    @Override
    public String toString() {
        return msgType;
    }
}
