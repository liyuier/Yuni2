package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.interfaces.detector.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: OrderOption
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 2:17
 * @description: 命令行选项
 */

@Data
@AllArgsConstructor
public class OrderOption implements OrderElement {

    // 选项的名称，供 OrderMatchedOut 使用
    private String name;

    // 选项的标识，即能够触发该选项的字符串
    private String flag;

    // 选项携带的参数
    private OrderArgContainer optionArgs;

    // 帮助信息
    private String helpInfo;

    public OrderOption() {
        optionArgs = new OrderArgContainer();
    }

    @Override
    public Boolean valid() {
        return null != name && !name.isEmpty()
                && null != flag && optionArgs.valid();
    }
}
