package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEventPosition;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: MessageEventHandler
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2024/11/11 22:33
 * @description: 消息事件 handler
 */

@Component
@EventHandler(eventType = MessageEvent.class)
public class MessageEventHandler {

    @Autowired
    PluginManager pluginManager;

    public <T extends MessageEvent<?>> void handle(T messageEvent) {
        preHandle(messageEvent);
        pluginManager.matchEventForPlugin(messageEvent);
        System.out.println("解析消息成功");
    }

    private <T extends MessageEvent<?>> void preHandle(T messageEvent) {
        // 设置消息链
        messageEvent.setMessageChain(new MessageChain(messageEvent.getMessage()));
        // 设置消息位置（群聊还是私聊）
        if (messageEvent instanceof GroupMessageEvent) {
            messageEvent.setPosition(new MessageEventPosition(MessageTypeEnum.GROUP, ((GroupMessageEvent) messageEvent).getGroupId()));
        } else {
            messageEvent.setPosition(new MessageEventPosition(MessageTypeEnum.PRIVATE, messageEvent.getSender().getUserId()));
        }
    }
}
