package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: UserRoleEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 20:26
 * @description: 用户身份枚举类
 */
@Getter
public enum UserRoleEnum {

    OWNER("owner", "群主"),
    ADMIN("admin", "管理员"),
    MEMBER("member", "普通成员");

    private final String role;
    private final String description;

    UserRoleEnum(String role, String description) {
        this.role = role;
        this.description = description;
    }

    @Override
    public String toString() {
        return role;
    }
}
