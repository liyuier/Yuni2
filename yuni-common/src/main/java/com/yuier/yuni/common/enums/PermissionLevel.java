package com.yuier.yuni.common.enums;

/**
 * @Title: PermissionLevel
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/11/8 23:37
 * @description: 权限控制等级
 */

public enum PermissionLevel {
    /**
     * 黑名单成员
     * 降下神罚！
     */
    BANNED,

    /**
     * 普通成员
     * 你过关
     */
    USER,

    /**
     * 管理员
     * 人人平等，但有些人比其他人更平等
     */
    ADMIN,

    /**
     * 超级管理员
     * 这背后一定存在某种不可告人的 PY 交易
     */
    SUPER_ADMIN,

    /**
     * Bot 拥有者
     * 一切都是主人的任务罢了
     */
    MASTER;

    public boolean lowerThan(PermissionLevel level) {
        return this.compareTo(level) < 0;
    }
    public boolean higherThan(PermissionLevel level) {
        return this.compareTo(level) > 0;
    }
}
