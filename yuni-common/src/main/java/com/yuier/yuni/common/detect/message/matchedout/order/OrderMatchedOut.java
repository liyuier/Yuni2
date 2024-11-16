package com.yuier.yuni.common.detect.message.matchedout.order;

import com.yuier.yuni.common.interfaces.messagematchedout.MessageMatchedOut;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Title: OrderMatchedOut
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.matchedout
 * @Date 2024/11/9 22:44
 * @description: 指令解析结果类
 */

@Data
@AllArgsConstructor
public class OrderMatchedOut implements MessageMatchedOut {
    // 指令参数集合
    private OrderArgsMatchedOut args;

    // 指令选项集合
    private OrderOptionsMatchedOut options;

    public OrderMatchedOut() {
        args = new OrderArgsMatchedOut();
        options = new OrderOptionsMatchedOut();
    }

    public OrderArgMatchedOut getArgByName(String argName) {
        return args.getArgMap().get(argName);
    }

    public Boolean argExists(String argName) {
        return args.getArgMap().containsKey(argName);
    }

    public Boolean optionExists(String optName) {
        return options.getOptionMap().containsKey(optName);
    }

    public Boolean optionArgExists(String optName, String argName) {
        return optionExists(optName) && getOptionByName(optName).argExists(argName);
    }

    public OrderOptionMatchedOut getOptionByName(String optName) {
        return options.getOptionMap().get(optName);
    }

    public OrderArgMatchedOut getOptionArgByName(String optName, String argName) {
        return options.getOptionMap().get(optName).getArgs().getArgMap().get(argName);
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

    /**
     * 添加被匹配出的参数选项
     * @param optionList  参数消息段
     */
    public void addOrderOptionsMatchedOut(ArrayList<OrderOptionMatchedOut> optionList) {
        HashMap<String, OrderOptionMatchedOut> optionMap = options.getOptionMap();
        for (OrderOptionMatchedOut optionMatchedOut : optionList) {
            optionMap.put(optionMatchedOut.getName(), optionMatchedOut);
        }
    }

}
