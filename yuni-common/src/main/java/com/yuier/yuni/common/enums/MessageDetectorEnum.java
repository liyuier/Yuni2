package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MessageDetectorEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/5 19:45
 * @description: 消息链探测器枚举类
 */
@Getter
public enum MessageDetectorEnum {

    BASE("base", "基础消息链探测器"),
    ORDER("order", "指令探测器");

    private final String detectorId;
    private final String description;

    MessageDetectorEnum(String detectorId, String description) {
        this.detectorId = detectorId;
        this.description = description;
    }

    @Override
    public String toString() {
        return detectorId;
    }
}
