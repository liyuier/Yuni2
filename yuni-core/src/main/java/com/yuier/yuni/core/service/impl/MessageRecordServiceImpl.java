package com.yuier.yuni.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.PrivateMessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.event.messagesent.GroupMessageSentEvent;
import com.yuier.yuni.common.domain.event.messagesent.MessageSentEvent;
import com.yuier.yuni.common.utils.BeanCopyUtils;
import com.yuier.yuni.core.mapper.MessageRecordMapper;
import com.yuier.yuni.core.service.MessageRecordService;
import com.yuier.yuni.core.domain.entity.MessageRecordEntity;
import org.springframework.stereotype.Service;

/**
 * (MessageRecord)表服务实现类
 *
 * @author liyuier
 * @since 2025-04-16 23:21:58
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecordEntity> implements MessageRecordService {

    private static final Integer MESSAGE_NOT_SENT_BY_BOT = 0;
    private static final Integer MESSAGE_SENT_BY_BOT = 1;

    /**
     * 持久化收到的消息
     * @param messageEvent  消息事件
     * @param <T>  消息事件类型
     */
    @Override
    public <T extends MessageEvent<?>> void saveMessage(T messageEvent) {

        /*
        为实体类初步赋值，应包括以下内容：
          - time
          - selfId
          - userId
          - rawMessage
         */
        MessageRecordEntity messageRecordEntity = BeanCopyUtils.copyBean(messageEvent, MessageRecordEntity.class);
        messageRecordEntity.setSentByBot(MESSAGE_NOT_SENT_BY_BOT);

        // 为实体类赋 groupId
        trySetGroupId(messageEvent, messageRecordEntity);
        // 为实体类赋值 text
        trySetText(messageEvent, messageRecordEntity);
        // 保存
        save(messageRecordEntity);
    }

    @Override
    public <T extends MessageSender> void saveMessage(MessageSentEvent<T> messageSentEvent) {
        MessageRecordEntity messageRecordEntity = BeanCopyUtils.copyBean(messageSentEvent, MessageRecordEntity.class);
        messageRecordEntity.setSentByBot(MESSAGE_SENT_BY_BOT);
        MessageEvent<?> messageEvent = null;
        if (messageSentEvent instanceof GroupMessageSentEvent) {
            messageEvent = BeanCopyUtils.copyBean(messageSentEvent, GroupMessageEvent.class);
        } else {
            messageEvent = BeanCopyUtils.copyBean(messageSentEvent, PrivateMessageEvent.class);
        }
        // 为实体类赋 groupId
        trySetGroupId(messageEvent, messageRecordEntity);
        // 为实体类赋值 text
        trySetText(messageEvent, messageRecordEntity);
        save(messageRecordEntity);
    }

    /**
     * 尝试为实体类的 groupId 字段赋值
     * @param messageEvent  消息事件
     * @param messageRecordEntity  消息记录实体类
     * @param <T>  消息事件类型
     */
    private <T extends MessageEvent<?>> void trySetGroupId(T messageEvent, MessageRecordEntity messageRecordEntity) {
        if (messageEvent instanceof GroupMessageEvent) {
            messageRecordEntity.setGroupId(
                    ((GroupMessageEvent) messageEvent).getGroupId()
            );
        }
    }

    /**
     * 尝试为实体类的 text 字段赋值
     * @param messageEvent  消息事件
     * @param messageRecordEntity  消息记录实体类
     * @param <T>  消息事件类型
     */
    private <T extends MessageEvent<?>> void trySetText(T messageEvent, MessageRecordEntity messageRecordEntity) {
        MessageChain messageChain = messageEvent.getMessageChain();
        messageRecordEntity.setText(messageChain.getPlainText());
    }
}

