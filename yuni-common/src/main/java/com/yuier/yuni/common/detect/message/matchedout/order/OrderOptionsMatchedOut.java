package com.yuier.yuni.common.detect.message.matchedout.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @Title: OrderOptionsMatchedOut
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.matchedout.order
 * @Date 2024/11/15 0:22
 * @description: 消息命中后，指令探测器解析出来的指令选项集合
 */

@Data
@AllArgsConstructor
public class OrderOptionsMatchedOut {

    // 选项集合
    private HashMap<String, OrderOptionMatchedOut> optionMap;

    public OrderOptionsMatchedOut() {
        optionMap = new HashMap<>();
    }
}
