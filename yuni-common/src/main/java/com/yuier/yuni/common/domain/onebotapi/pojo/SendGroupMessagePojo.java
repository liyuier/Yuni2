package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: SendGroupMessagePojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/4/16 0:35
 * @description: 发送群聊消息时发送的实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendGroupMessagePojo {
    private Long groupId;
    private ArrayList<MessageSeg<?>> message;
    private Boolean autoEscape = false;

    public SendGroupMessagePojo(Long groupId, ArrayList<MessageSeg<?>> message) {
        this.groupId = groupId;
        this.message = message;
    }
    public SendGroupMessagePojo(Long groupId, MessageChain chain) {
        this(groupId, chain.getContent());
    }
    public SendGroupMessagePojo(Long groupId, MessageSeg<?>[] messageSegs) {
        this(groupId, new MessageChain(messageSegs));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg<?> seg : message) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }
}
