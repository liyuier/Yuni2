package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: MetaEventEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/20 19:06
 * @description: OneBot 元事件类型枚举类
 *               参考 https://283375.github.io/onebot_v11_vitepress/event/meta.html 每种事件 meta_event_type 字段
 */
@Getter
public enum MetaEventEnum {

    LIFECYCLE("lifecycle", "生命周期"),
    HEARTBEAT("heartbeat", "心跳");

    private final String metaEventType;
    private final String description;

    MetaEventEnum(String metaEventType, String description) {
        this.metaEventType = metaEventType;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.metaEventType;
    }

}
