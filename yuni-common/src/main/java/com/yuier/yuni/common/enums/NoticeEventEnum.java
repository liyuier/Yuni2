package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: NoticeEventEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 19:11
 * @description: OneBot 通知事件枚举类
 */
@Getter
public enum NoticeEventEnum {
    GROUP_UPLOAD("group_upload", "群文件上传"),
    GROUP_ADMIN("GROUP_ADMIN", "群管理员变动"),
    GROUP_DECREASE("group_decrease", "群成员减少"),
    GROUP_INCREASE("group_increase", "群成员增加"),
    GROUP_BAN("group_ban", "群禁言"),
    FRIEND_ADD("friend_add", "好友添加"),
    GROUP_RECALL("group_recall", "群消息撤回"),
    FRIEND_RECALL("friend_recall", "好友消息撤回"),
    NOTIFY("notify", "群内戳一戳 / 群红包运气王 / 群成员荣誉变更");

    private final String noticeType;
    private final String description;

    NoticeEventEnum(String noticeType, String description) {
        this.noticeType = noticeType;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.noticeType;
    }

}
