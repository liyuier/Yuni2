package com.yuier.yuni.common.detect.message.matchedout.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Title: OrderOptionMatchedOut
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.matchedout.order
 * @Date 2024/11/15 0:22
 * @description: 消息命中后，指令探测器解析出来的单个指令选项
 */

@Data
@AllArgsConstructor
public class OrderOptionMatchedOut {
    // 选项名称
    private String name;

    // 选项参数集合
    private OrderArgsMatchedOut args;

    // 选项帮助信息
    private String helpInfo;

    public OrderOptionMatchedOut() {
        name = "";
        args = new OrderArgsMatchedOut();
        helpInfo = "";
    }

    public OrderOptionMatchedOut(String name, String helpInfo) {
        this.name = name;
        args = new OrderArgsMatchedOut();
        this.helpInfo = helpInfo;
    }

    public Boolean argExists(String argName) {
        return args.getArgMap().containsKey(argName);
    }

    public OrderArgMatchedOut getArgByName(String argName) {
        return args.getArgMap().get(argName);
    }

    /**
     * 添加被匹配出的参数消息段
     * @param argList  参数消息段
     */
    public void addOrderArgsMatchedList(ArrayList<OrderArgMatchedOut> argList) {
        HashMap<String, OrderArgMatchedOut> argMap = args.getArgMap();
        for (OrderArgMatchedOut argMatchedOut : argList) {
            argMap.put(argMatchedOut.getName(), argMatchedOut);
        }
    }
}
