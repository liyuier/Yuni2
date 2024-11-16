package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.detector.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: OrderArg
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 1:13
 * @description: 指令参数父类
 */

@Data
@AllArgsConstructor
public class OrderArg implements OrderElement {

    /**
     * 参数名称，供 OrderMatchedOut 使用
     */
    private String name;

    /**
     * 参数可以接受的消息数据类型
     */
    private OrderArgAcceptType acceptType;

    /**
     * 帮助信息
     */
    private String helpInfo;

    private OrderArg() {
        acceptType = OrderArgAcceptType.TEXT;
    }

    public OrderArg(String name) {
        this();
        this.name = name;
    }

    public OrderArg(String name, OrderArgAcceptType acceptType) {
        this.name = name;
        this.acceptType = acceptType;
    }

    public OrderArg(String name, String helpInfo) {
        this();
        this.name = name;
        this.helpInfo = helpInfo;
    }

    public Boolean wantsMessageType(OrderArgAcceptType typeEnum) {
        return getAcceptType().equals(typeEnum);
    }

    @Override
    public Boolean valid() {
        return null != name && !name.isEmpty();
    }
}
