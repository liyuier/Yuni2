package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
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

    public <T extends MessageEvent> void handle(T messageEvent) {
        System.out.println("OK");
    }
}
