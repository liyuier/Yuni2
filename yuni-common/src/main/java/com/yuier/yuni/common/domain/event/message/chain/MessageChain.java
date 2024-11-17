package com.yuier.yuni.common.domain.event.message.chain;

import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Title: MessageChain
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/10 23:24
 * @description: 消息链
 */

@Data
@AllArgsConstructor
public class MessageChain {

    private ArrayList<MessageSeg<?>> content;

    public MessageChain() {
        content = new ArrayList<>();
    }

    public MessageChain(MessageSeg<?>[] messageSegs) {
        this.content = (ArrayList<MessageSeg<?>>) Arrays.asList(messageSegs);
    }

    public MessageChain(String text) {
        this();
        content.add(new TextSeg(text));
    }
}
