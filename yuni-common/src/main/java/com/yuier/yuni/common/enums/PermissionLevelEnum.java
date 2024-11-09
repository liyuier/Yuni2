package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: PermissionLevelEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/15 0:50
 * @description: 权限控制等级枚举
 */
@Getter
public enum PermissionLevelEnum {
    BANNED(0, "黑名单成员"),
    USER(1, "普通用户"),
    ADMIN(2, "管理员"),
    SUPER_ADMIN(3, "超级管理员"),
    OWNER(4, "bot 主人");

    private final int level;
    private final String description;

    PermissionLevelEnum(int level, String description) {
        this.level = level;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.valueOf(level);
    }
}
