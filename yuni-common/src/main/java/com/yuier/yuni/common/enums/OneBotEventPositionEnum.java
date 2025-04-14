package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: OneBotEventPositionEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2025/4/14 23:50
 * @description: OneBot 事件发生位置枚举
 */

@Getter
public enum OneBotEventPositionEnum {

    PRIVATE("private", "私聊"),
    GROUP("group", "群聊");

    private final String positionType;
    private final String description;

    OneBotEventPositionEnum(String positionType, String description) {
        this.positionType = positionType;
        this.description = description;
    }

    @Override
    public String toString() {
        return positionType;
    }
}
