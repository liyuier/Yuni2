package com.yuier.yuni.common.detect.message.matchedout.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @Title: OrderArgsMatchedOut
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.matchedout
 * @Date 2024/11/15 0:12
 * @description: 消息命中后，指令探测器解析出来的指令参数集合
 */

@Data
@AllArgsConstructor
public class OrderArgsMatchedOut {

    // 参数集合
    private HashMap<String, OrderArgMatchedOut> argMap;

    public OrderArgsMatchedOut() {
        argMap = new HashMap<>();
    }
}
