package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: YuniOrderArgContentTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/11 0:14
 * @description: 指令参数接收的内容类型枚举类
 */
@Getter
public enum YuniOrderArgContentTypeEnum {

    TEXT("text", "字符串文本"),
    NUMBER("number", "数字"),
    AT("at", "@ 用户"),
    IMAGE("image", "图片"),
    URL("url", "url"),
    REPLY("reply", "回复消息");

    private final String type;
    private final String description;

    YuniOrderArgContentTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return type;
    }
}
