package com.yuier.yuni.common.enums;

/**
 * @Title: TriggerEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/11/9 0:53
 * @description: 触发插件的事件
 */
public enum TriggerEvent {
    // 任意消息事件
    ANY_MESSAGE,

    // 群消息事件
    GROUP_MESSAGE,

    // 私聊消息事件
    PRIVATE_MESSAGE,

    // 通知事件
    NOTICE,

    // 请求事件
    REQUEST,

    // 元事件
    META_EVENT
}
