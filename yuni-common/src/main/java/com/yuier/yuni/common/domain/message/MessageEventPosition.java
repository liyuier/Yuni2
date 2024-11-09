package com.yuier.yuni.common.domain.message;

import com.yuier.yuni.common.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: MessageChainPosition
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message
 * @Date 2024/5/29 23:25
 * @description: 消息链来源
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEventPosition {
    // 消息链来源
    private MessageTypeEnum messageType;
    /**
     * 消息链位置
     * 如果消息链来自群聊，则为群号；
     * 如果消息链来自私聊，则为 QQ 号
     */
    private Long position;
}
