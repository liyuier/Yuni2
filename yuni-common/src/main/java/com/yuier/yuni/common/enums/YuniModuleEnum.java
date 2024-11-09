package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: YuniModuleEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/14 21:51
 * @description: 模块枚举类（迫不得已）
 */
@Getter
public enum YuniModuleEnum {

    FUNCTION("function", "普通功能模块"),
    DD("dd", "DD 模块"),
    CORE("core", "核心模块");

    private final String name;
    private final String description;

    YuniModuleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
