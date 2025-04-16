package com.yuier.yuni.common.domain.event.messagesent;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.AnonymousMessage;
import com.yuier.yuni.common.domain.event.message.sender.GroupMessageSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: GroupMessageSentEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.messagesent
 * @Date 2025/4/16 22:51
 * @description: BOT 发送群聊消息事件
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeDefine("group")
public class GroupMessageSentEvent extends MessageSentEvent<GroupMessageSender> {
    // 群号
    private Long groupId;

    // 匿名消息，如果不是匿名消息则为 null
    private AnonymousMessage anonymous;
}
