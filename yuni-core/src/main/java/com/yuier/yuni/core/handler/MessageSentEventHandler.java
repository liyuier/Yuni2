package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.event.messagesent.MessageSentEvent;
import com.yuier.yuni.common.utils.YuniLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Title: MessageSentEventHandler
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2025/4/16 22:31
 * @description: 消息发送事件处理器
 */

@Slf4j
@Component
@EventHandler(eventType = MessageEvent.class)
public class MessageSentEventHandler {

    public <T extends MessageSender> void handle(MessageSentEvent<T> messageSentEvent) {
        // 设置消息链
        messageSentEvent.setMessageChain(new MessageChain(messageSentEvent.getMessage(), true));
        // 打印消息日志
        log.info(YuniLogUtils.receiveMessageSentEvent(messageSentEvent));
    }

}
