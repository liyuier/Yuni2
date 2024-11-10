package com.yuier.yuni.common.domain.event.message.chain;

import com.yuier.yuni.common.domain.event.message.MessageSeg;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Title: ReMessageChain
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/10 23:24
 * @description: 消息链
 */

@Data
@Component
@AllArgsConstructor
public class ReMessageChain {

    private ArrayList<MessageSeg> content;
}
