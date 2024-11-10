package com.yuier.yuni.common.domain.event.message.sender;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: PrivateMessageSender
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message
 * @Date 2024/11/10 21:33
 * @description: 私聊消息 sender 字段
 * OneBot11 标准中，群聊消息 sender 字段为私聊消息 sender 字段的超集
 * 因此在 MessageSender 中定义了私聊 sender 的所有字段，此处无需额外再定义字段
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PrivateMessageSender extends MessageSender {

}
