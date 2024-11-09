package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MetaEventSubTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 21:11
 * @description: 元事件子类型枚举类
 */
@Getter
public enum MetaEventSubTypeEnum {

    ENABLE("enable", "OneBot 启用"),
    DISABLE("disable", "OneBot 停用"),
    CONNECT("connect", "WebSocket 连接成功");

    private final String subType;
    private final String description;

    MetaEventSubTypeEnum(String subType, String description) {
        this.subType = subType;
        this.description = description;
    }

    @Override
    public String toString() {
        return subType;
    }
}
