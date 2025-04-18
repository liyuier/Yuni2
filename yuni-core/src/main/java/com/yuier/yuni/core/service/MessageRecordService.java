package com.yuier.yuni.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.event.messagesent.MessageSentEvent;
import com.yuier.yuni.core.domain.entity.MessageRecordEntity;

/**
 * (MessageRecord)表服务接口
 *
 * @author liyuier
 * @since 2025-04-16 23:21:58
 */
public interface MessageRecordService extends IService<MessageRecordEntity> {

    <T extends MessageEvent<?>> void saveMessage(T messageEvent);

    <T extends MessageSender> void saveMessage(MessageSentEvent<T> messageSentEvent);

    MessageChain queryMessageByMessageId(Long messageId);
}

