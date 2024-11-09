package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: GroupHonorEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 21:05
 * @description: 群荣誉枚举类
 */
@Getter
public enum GroupHonorEnum {

    TALKACTIVE("talkative", "龙王"),
    PERFORMER("performer", "群聊之火"),
    EMOTION("emotion", "快乐源泉");

    private final String honor;
    private final String description;

    GroupHonorEnum(String honor, String description) {
        this.honor = honor;
        this.description = description;
    }

    @Override
    public String toString() {
        return honor;
    }
}
