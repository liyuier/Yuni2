package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: YuniOrderArgsTypeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/9 22:29
 * @description: 指令参数是否必需枚举类
 */
@Getter
public enum YuniOrderArgRequireTypeEnum {

    REQUIRED("required", "必选参数"),
    OPTIONAL("optional", "可选参数");

    /**
     * 参数类型分为预置参数与自定义参数
     * 预置参数由指令定义者预先设置好可选的值，只有参数与预置值相符才能命中
     * 自定义参数由用户输入
     */
    private final String type;
    private final String description;

    YuniOrderArgRequireTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return type;
    }
}
