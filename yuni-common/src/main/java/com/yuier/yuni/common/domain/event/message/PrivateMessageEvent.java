package com.yuier.yuni.common.domain.event.message;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.sender.PrivateMessageSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: PrivateMessageEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message
 * @Date 2024/11/10 21:49
 * @description: 私聊消息事件
 * OneBot11 标准中，群聊消息字段为私聊消息字段的超集，且公用字段中只有 sender 字段二者不同
 * 因此在 ReMessageEvent 中定义了所有私聊消息的字段，除了 sender 字段，该字段由私聊、群聊消息事件类分别处理
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeDefine("private")
public class PrivateMessageEvent extends ReMessageEvent {
    // 发送人信息
    private PrivateMessageSender sender;
}
