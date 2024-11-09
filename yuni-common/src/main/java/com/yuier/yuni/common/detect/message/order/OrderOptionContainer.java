package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.interfaces.detector.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Title: OrderOptionContainer
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 2:16
 * @description: 命令行选项集合
 */

@Data
@AllArgsConstructor
public class OrderOptionContainer implements OrderElement {

    private ArrayList<OrderOption> optionList;

    public OrderOptionContainer() {
        optionList = new ArrayList<>();
    }

    /**
     * 定义一个选项时，必须同时给定选项名称与选项 flag.
     * 本方法用于校验选项和 flag 是否有效
     * @param optName 待校验的选项名称
     * @param optFlag 待校验的选项标识
     */
    public void checkNameAndFlagValid(String optName, String optFlag) {
        if (null == optName || optName.isEmpty() ||
                null == optFlag || optFlag.isEmpty()) {
            throw new RuntimeException("选项名称与标识均不能为空！");
        }
        for (OrderOption option : optionList) {
            if (option.getName().equals(optName)) {
                throw new RuntimeException("选项名称" + optName + "已存在！");
            }
            if (option.getFlag().equals(optFlag)) {
                throw new RuntimeException("选项标识" + optFlag + "已存在！");
            }
        }
    }

    @Override
    public Boolean valid() {
        return null;
    }
}
