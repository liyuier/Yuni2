package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.randosoru.plugin.PluginManager;
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

    public <T extends MessageEvent> void handle(T messageEvent) {
        messageEvent.setMessageChain(new MessageChain(messageEvent.getMessage()));
        pluginManager.matchMessageEvent(messageEvent);
        System.out.println("OK");
    }
}
