package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.detector.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Title: OrderOptionContainer
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 2:16
 * @description: 指令选项集合
 * @Detail
 * 指令选项某种意义上可以视作一条 “微型指令”。
 * 选项标识对应指令头，选项参数与指令参数完全可以复用
 */

@Data
public class OrderOptionContainer implements OrderElement {

    private static ArrayList<OrderOption> optionList;

    public OrderOptionContainer() {
        optionList = new ArrayList<>();
    }

    /**
     * 定义一个选项时，必须同时给定选项名称与选项 flag.
     * 本方法用于校验选项和 flag 是否有效
     * @param optName 待校验的选项名称
     * @param optFlag 待校验的选项标识
     */
    public static void checkNameAndFlagValid(String optName, String optFlag) {
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

    public void addOption(OrderOption option) {
        optionList.add(option);
    }

    public static class OptionBuilder {
        // 选项的名称，供 OrderMatchedOut 使用
        private String name;

        // 选项的标识，即能够触发该选项的字符串
        private String flag;

        // 选项携带的参数
        private OrderArgContainer optionArgs;

        // 帮助信息
        private String helpInfo;

        public OptionBuilder setNameAndFlag(String name, String flag) {
            checkNameAndFlagValid(name, flag);
            this.name = name;
            this.flag = flag;
            return this;
        }

        public OptionBuilder addRequiredArg(String argName) {
            optionArgs.addRequiredArg(argName);
            return this;
        }

        public OptionBuilder addRequiredArg(String argName, OrderArgAcceptType contentType) {
            optionArgs.addRequiredArg(argName, contentType);
            return this;
        }

        public OptionBuilder addRequiredArg(String argName, String helpInfo) {
            optionArgs.addRequiredArg(argName, helpInfo);
            return this;
        }

        public OptionBuilder addRequiredArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            optionArgs.addRequiredArg(argName, contentType, helpInfo);
            return this;
        }

        public OptionBuilder addRequiredArg(RequiredArg arg) {
            optionArgs.addRequiredArg(arg);
            return this;
        }

        public OptionBuilder addOptionalArg(String argName) {
            optionArgs.addOptionalArg(argName);
            return this;
        }

        public OptionBuilder addOptionalArg(String argName, OrderArgAcceptType contentType) {
            optionArgs.addOptionalArg(argName, contentType);
            return this;
        }

        public OptionBuilder addOptionalArg(String argName, String helpInfo) {
            optionArgs.addOptionalArg(argName, helpInfo);
            return this;
        }

        public OptionBuilder addOptionalArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            optionArgs.addOptionalArg(argName, contentType, helpInfo);
            return this;
        }

        public OptionBuilder addOptionalArg(OptionalArg arg) {
            optionArgs.addOptionalArg(arg);
            return this;
        }

        public OptionBuilder addHelpInfo(String helpInfo) {
            this.helpInfo = helpInfo;
            return this;
        }

        public OrderOption build() {
            return new OrderOption(this.name, this.flag, this.optionArgs, this.helpInfo);
        }
    }

    @Override
    public Boolean valid() {
        for (OrderOption option : optionList) {
            if (!option.valid()) {
                return false;
            }
        }
        return true;
    }
}
