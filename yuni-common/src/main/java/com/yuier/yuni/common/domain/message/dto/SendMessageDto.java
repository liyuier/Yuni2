package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.MessageChain;
import com.yuier.yuni.common.domain.message.MessageEventPosition;
import com.yuier.yuni.common.domain.message.MessageSeg;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: SendPrivateMsgDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/16 0:09
 * @description: 发送消息时发送的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendMessageDto {
    // 消息类型 private / group
    private String messageType;
    private Long userId;
    private Long groupId;
    private ArrayList<MessageSeg> message;
    // 消息内容是否作为纯文本发送（即不解析 CQ 码）
    private boolean autoEscape;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg seg : message) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }

    public SendMessageDto(MessageEventPosition messageEventPosition, MessageChain chain) {
        if (messageEventPosition.getMessageType().equals(MessageTypeEnum.PRIVATE)) {
            this.messageType = "private";
            this.userId = messageEventPosition.getPosition();
        } else if (messageEventPosition.getMessageType().equals(MessageTypeEnum.GROUP)) {
            this.messageType = "group";
            this.groupId = messageEventPosition.getPosition();
        }
        this.message = chain.getContent();
    }
}
