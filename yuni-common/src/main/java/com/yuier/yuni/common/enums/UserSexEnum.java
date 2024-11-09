package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: UserSexEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 20:23
 * @description: 用户性别枚举类
 */
@Getter
public enum UserSexEnum {

    MALE("male", "男性"),
    FEMALE("female", "女性"),
    UNKNOWN("unknown", "未知");

    private final String sex;
    private final String description;

    UserSexEnum(String sex, String description) {
        this.sex = sex;
        this.description = description;
    }

    @Override
    public String toString() {
        return sex;
    }
}
