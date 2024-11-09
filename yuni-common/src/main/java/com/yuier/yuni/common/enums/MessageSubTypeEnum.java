package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MsgSubTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 20:19
 * @description: 消息事件子类型枚举类
 */
@Getter
public enum MessageSubTypeEnum {

    PRIVATE_FRIEND("friend", "好友私聊消息"),
    PRIVATE_GROUP("group", "群临时会话私聊消息"),
    PRIVATE_OTHER("other", "其它私聊消息"),
    GROUP_NORMAL("normal", "正常群聊消息"),
    GROUP_ANONYMOUS("anonymous", "匿名群聊消息"),
    GROUP_NOTICE("notice", "系统提示群聊消息");

    private final String msgSubType;
    private final String description;

    MessageSubTypeEnum(String msgSubType, String description) {
        this.msgSubType = msgSubType;
        this.description = description;
    }

    @Override
    public String toString() {
        return msgSubType;
    }
}
