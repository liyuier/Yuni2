package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: NoticeSubTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 20:31
 * @description: 通知事件子事件枚举类
 */
@Getter
public enum NoticeSubTypeEnum {

    GROUP_ADMIN_SET("set", "群管理员设置"),
    GROUP_ADMIN_UNSET("unset", "群管理员撤销"),
    GROUP_DECREASE_LEAVE("leave", "群成员主动退群"),
    GROUP_DECREASE_KICK("kick", "群成员被踢"),
    GROUP_DECREASE_KICK_ME("kick_me", "机器人自己被踢"),
    GROUP_INCREASE_APPROVE("approve", "管理员同意入群"),
    GROUP_INCREASE_INCITE("invite", "管理员邀请入群"),
    GROUP_BAN_BAN("ban", "实施禁言"),
    GROUP_BAN_LIFT_BAN("lift_ban", "解除禁言"),
    NOTIFY_POKE("poke", "群内戳一戳"),
    NOTIFY_LUCKY_KING("lucky_king", "群红包运气王"),
    NOTIFY_HONOR("honor", "群成员荣誉变更");

    private final String subType;
    private final String description;

    NoticeSubTypeEnum(String subType, String description) {
        this.subType = subType;
        this.description = description;
    }

    @Override
    public String toString() {
        return subType;
    }
}
