package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.enums.OrderArgAcceptType;

/**
 * @Title: OptionalArg
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 1:53
 * @description: 可选参数
 */
public class OptionalArg extends OrderArg{
    public OptionalArg(String name) {
        super(name);
    }

    public OptionalArg(String name, OrderArgAcceptType acceptType) {
        super(name, acceptType);
    }

    public OptionalArg(String name, String helpInfo) {
        super(name, helpInfo);
    }

    public OptionalArg(String name, OrderArgAcceptType acceptType, String helpInfo) {
        super(name, acceptType, helpInfo);
    }

    public String getHelpInfo() {
        return super.getHelpInfo() == null ?
                "当前参数为必选参数，可接受消息类型为 " + getAcceptType() :
                getHelpInfo();
    }
}
