package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.interfaces.detector.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: OrderHead
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.definer.order
 * @Date 2024/11/9 23:11
 * @description: 指令标识符，即指令的第一个字段
 *               用于标识一个指令，通常每个指令的指令头是唯一的
 */

@Data
@AllArgsConstructor
public class OrderHead implements OrderElement {

    // 指令头字符串
    private String name;

    public OrderHead() {
        name = "";
    }

    @Override
    public Boolean valid() {
        return name != null && !name.isEmpty();
    }
}
