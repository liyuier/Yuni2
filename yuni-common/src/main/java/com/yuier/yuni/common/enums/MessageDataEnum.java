package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MsgDataEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 18:17
 * @description: 消息段类型枚举类
 */
@Getter
public enum MessageDataEnum {

    ANONYMOUS("anonymous", "匿名消息（发）"),
    AT("at", "@某人"),
    CONTACT("contact", "推荐好友"),
    DICE("dice", "掷骰子魔法表情"),
    FACE("face", "QQ 表情"),
    FILE("file", "文件"),
    FORWARD("forward", "合并转发（收）"),
    IMAGE("image", "图片"),
    JSON("json", "JSON 消息"),
    LOCATION("location", "位置"),
    MARKDOWN("markdown", "Markdown 消息"),
    MARKETFACE("mface", "商城表情"),
    MUSIC("music", "音乐分享（发）"),
    NODE("node", "合并转发节点（发）"),
    POKE("poke", "戳一戳"),
    RECORD("record", "语音"),
    REPLY("reply", "回复"),
    RPS("rps", "猜拳魔法表情"),
    SHAKE("shake", "窗口抖动（戳一戳）（发）"),
    SHARE("share", "链接分享"),
    TEXT("text", "纯文本"),
    VIDEO("video", "短视频"),
    XML("xml", "XML 消息");

    private final String typeName;
    private final String description;

    MessageDataEnum(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

}
