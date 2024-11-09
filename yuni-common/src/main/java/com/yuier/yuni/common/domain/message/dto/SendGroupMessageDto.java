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
 * @Title: SendGroupMessageDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/16 0:35
 * @description: 发送群聊消息时发送的实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendGroupMessageDto {
    private Long groupId;
    private ArrayList<MessageSeg> message;
    private Boolean autoEscape = false;

    public SendGroupMessageDto(Long groupId, ArrayList<MessageSeg> message) {
        this.groupId = groupId;
        this.message = message;
    }
    public SendGroupMessageDto(Long groupId, MessageChain chain) {
        this.groupId = groupId;
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
