package com.yuier.yuni.common.domain.event.message;

import com.yuier.yuni.common.domain.event.message.chain.seg.data.MessageData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: OneBotMessageSegDto
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.dto
 * @Date 2024/4/10 1:21
 * @description: OneBot 消息段类型
 * @Detail
 * 消息段为组成一条消息链的单位
 * 典型的消息段结构如下
 * {
 *     "type": "text",
 *     "data": {
 *         "text": "纯文本内容"
 *     }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSeg {

    // 消息段类型
    private String type;

    // 消息段数据
    private MessageData data;

    public Boolean typeOf(MessageDataEnum dataType) {
        return type.equals(dataType.getTypeName());
    }
}
