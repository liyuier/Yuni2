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
 * @Title: SendPrivateMessagePojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/4/16 0:33
 * @description: 发送私聊消息时发送的实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendPrivateMessagePojo {
    private Long userId;
    private ArrayList<MessageSeg<?>> message;
    private boolean autoEscape;

    public SendPrivateMessagePojo(Long userId, ArrayList<MessageSeg<?>> message) {
        this.userId = userId;
        this.message = message;
    }

    public SendPrivateMessagePojo(Long userId, MessageChain chain) {
        this(userId, chain.getContent());
    }

    public SendPrivateMessagePojo(Long userId, MessageSeg<?>[] messageSegs) {
        this(userId, new MessageChain(messageSegs));
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
