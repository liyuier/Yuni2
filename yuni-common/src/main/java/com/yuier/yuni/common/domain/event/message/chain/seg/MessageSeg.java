package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MessageData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: MessageSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/10 23:29
 * @description: 消息段
 * 典型的上报消息是这样的
 * message: [
 *     {
 *         "type": "text",
 *         "data": {
 *             "text": "[第一部分]"
 *         }
 *     },
 *     {
 *         "type": "image",
 *         "data": {
 *             "file": "123.jpg"
 *         }
 *     },
 * ]
 * 其中，message 键对应的数组 value 就是一个消息段数组
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,  // 使用子类名称来自动适配子类
        property = "type",  // 指定配置子类型的字段为 messageType
        defaultImpl = MessageEvent.class,  // 未设置 messageType 时默认的解析类型，这里设为 OneBotEvent 本身
        visible = true)  // 反序列化时 property 配置的字段是否解析出值放在结果中
public abstract class MessageSeg<T extends MessageData> {
    private String type;
    // 你他妈的真是个天才，二等饼干
    T data;
    public Boolean typeOf(MessageDataEnum messageDataEnum) {
        return type.equals(messageDataEnum.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageSeg<?> that = (MessageSeg<?>) o;
        return Objects.equals(type, that.type) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }
}
