package com.yuier.yuni.common.domain.event.messagesent;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.sender.PrivateMessageSender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: PrivateMessageSentEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.messagesent
 * @Date 2025/4/16 22:52
 * @description: BOT 发送私聊消息事件
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeDefine("private")
public class PrivateMessageSentEvent extends MessageSentEvent<PrivateMessageSender> {

}
