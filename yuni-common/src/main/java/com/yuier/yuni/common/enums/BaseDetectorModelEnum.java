package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: BaseDetectorModelEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/4 1:27
 * @description: 基础消息链探测器探测模式枚举类
 */
@Getter
public enum BaseDetectorModelEnum {

    AND("and", "与匹配模式"),
    OR("or", "或匹配模式");

    private final String modelName;
    private final String description;

    BaseDetectorModelEnum(String modelName, String description) {
        this.modelName = modelName;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.modelName;
    }
}
