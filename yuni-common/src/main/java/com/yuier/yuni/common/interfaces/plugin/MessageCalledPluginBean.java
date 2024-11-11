package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.interfaces.detector.MessageDetector;
import com.yuier.yuni.common.interfaces.messagematchedout.MessageMatchedOut;
import com.yuier.yuni.common.utils.BotAction;

/**
 * @Title: MessageCalledPluginBean
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2024/11/9 22:13
 * @description: 消息事件触发的插件接口
 */

public interface MessageCalledPluginBean<T extends MessageDetector> extends NegativePluginBean<MessageEvent, T> {

    // 监听的消息类型，默认同时监听私聊与群聊
    default MessageTypeEnum listenAt() {
        return MessageTypeEnum.ALL;
    }
}
