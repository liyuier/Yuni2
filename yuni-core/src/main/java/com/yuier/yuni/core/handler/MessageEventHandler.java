package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.OneBotEventPosition;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.enums.OneBotEventPositionEnum;
import com.yuier.yuni.common.utils.YuniLogUtils;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import com.yuier.yuni.core.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: MessageEventHandler
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2024/11/11 22:33
 * @description: 消息事件 handler
 */

@Slf4j
@Component
@EventHandler(eventType = MessageEvent.class)
public class MessageEventHandler {

    @Autowired
    PluginManager pluginManager;
    @Autowired
    MessageRecordService messageRecordService;

    public <T extends MessageEvent<?>> void handle(T messageEvent) {
        preHandle(messageEvent);
        pluginManager.matchEventForPlugin(messageEvent);
    }

    private <T extends MessageEvent<?>> void preHandle(T messageEvent) {
        // 设置消息链
        messageEvent.setMessageChain(new MessageChain(messageEvent.getMessage(), true));
        // 设置消息位置（群聊还是私聊）
        if (messageEvent instanceof GroupMessageEvent) {
            messageEvent.setPosition(new OneBotEventPosition(OneBotEventPositionEnum.GROUP, ((GroupMessageEvent) messageEvent).getGroupId()));
        } else {
            messageEvent.setPosition(new OneBotEventPosition(OneBotEventPositionEnum.PRIVATE, messageEvent.getSender().getUserId()));
        }
        // 持久化
        messageRecordService.saveMessage(messageEvent);
        // 打印消息日志
        log.info(YuniLogUtils.receiveMessageLogStr(messageEvent));
    }
}
