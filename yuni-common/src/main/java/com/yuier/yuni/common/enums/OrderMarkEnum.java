package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: OrderMarkEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/21 17:47
 * @description: 命令标识符枚举类
 */
@Getter
public enum OrderMarkEnum {

    ASTERISK("*", "星号"),
    SLASH("/", "斜杠"),
    DOT(".", "点"),
    SHARP("#", "井号");

    private final String mark;
    private final String description;

    OrderMarkEnum(String mark, String description) {
        this.mark = mark;
        this.description = description;
    }

    @Override
    public String toString() {
        return mark;
    }
}
