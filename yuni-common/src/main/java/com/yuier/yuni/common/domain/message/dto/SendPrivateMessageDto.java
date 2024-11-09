package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.MessageChain;
import com.yuier.yuni.common.domain.message.MessageSeg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: SendPrivateMessageDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/16 0:33
 * @description: 发送私聊消息时发送的实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendPrivateMessageDto {
    private Long userId;
    private ArrayList<MessageSeg> message;
    private boolean autoEscape;

    public SendPrivateMessageDto(Long userId, ArrayList<MessageSeg> message) {
        this.userId = userId;
        this.message = message;
    }

    public SendPrivateMessageDto(Long userId, MessageChain chain) {
        this.userId = userId;
        this.message = chain.getContent();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg seg : message) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }
}
