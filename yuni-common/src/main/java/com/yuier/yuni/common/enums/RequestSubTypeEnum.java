package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: RequestSubTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 21:09
 * @description: 加群请求事件子类型枚举类
 */
@Getter
public enum RequestSubTypeEnum {

    GROUP_ADD("add", "加群请求"),
    GROUP_INVITE("invite", "邀请进群请求");

    private final String subType;
    private final String description;

    RequestSubTypeEnum(String subType, String description) {
        this.subType = subType;
        this.description = description;
    }

    @Override
    public String toString() {
        return subType;
    }
}
