package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.event.message.MessageEventPosition;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: SendPrivateMsgPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/4/16 0:09
 * @description: 发送消息时发送的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendMessagePojo {
    // 消息类型 private / group
    private String messageType;
    private Long userId;
    private Long groupId;
    private ArrayList<MessageSeg<?>> message;
    // 消息内容是否作为纯文本发送（即不解析 CQ 码）
    private boolean autoEscape;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg<?> seg : message) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }

    public SendMessagePojo(MessageEventPosition messageEventPosition, MessageChain chain) {
        if (messageEventPosition.getMessageType().equals(MessageTypeEnum.PRIVATE)) {
            this.messageType = "private";
            this.userId = messageEventPosition.getPositionId();
        } else if (messageEventPosition.getMessageType().equals(MessageTypeEnum.GROUP)) {
            this.messageType = "group";
            this.groupId = messageEventPosition.getPositionId();
        }
        this.message = chain.getContent();
    }
}
