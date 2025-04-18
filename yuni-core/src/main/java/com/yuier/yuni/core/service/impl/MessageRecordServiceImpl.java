package com.yuier.yuni.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.PrivateMessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.event.messagesent.GroupMessageSentEvent;
import com.yuier.yuni.common.domain.event.messagesent.MessageSentEvent;
import com.yuier.yuni.common.utils.BeanCopyUtils;
import com.yuier.yuni.core.domain.entity.MessageRecordEntity;
import com.yuier.yuni.core.mapper.MessageRecordMapper;
import com.yuier.yuni.core.service.MessageRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

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

        // 设置 sentByBot 为否
        messageRecordEntity.setSentByBot(MESSAGE_NOT_SENT_BY_BOT);
        // 为实体化类赋格式化后的时间
        messageRecordEntity.setFormattedTime(new Date(messageRecordEntity.getTime() * 1000L));

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

        // 设置 sentByBot 为是
        messageRecordEntity.setSentByBot(MESSAGE_SENT_BY_BOT);
        // 为实体化类赋格式化后的时间
        Date date = new Date(messageRecordEntity.getTime());
        messageRecordEntity.setFormattedTime(new Date(messageRecordEntity.getTime() * 1000L));

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
     * 根据消息 ID 查询本地数据库中的消息记录，并组装为消息链
     * @param messageId  消息 ID
     * @return  组装出的消息链
     */
    @Override
    public MessageChain queryMessageByMessageId(Long messageId) {
        LambdaQueryWrapper<MessageRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageRecordEntity::getMessageId, messageId);
        MessageRecordEntity record = getOne(wrapper);
        String rawMessage = record.getRawMessage();
        return MessageChain.parseCQToChain(rawMessage);
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

