package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: RequestTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 21:07
 * @description: 请求事件类型枚举类
 */
@Getter
public enum RequestTypeEnum {

    FRIEND("friend", "加好友请求"),
    GROUP("group", "加群 / 邀请请求");

    private final String requestType;
    private final String description;

    RequestTypeEnum(String requestType, String description) {
        this.requestType = requestType;
        this.description = description;
    }

    @Override
    public String toString() {
        return requestType;
    }
}
