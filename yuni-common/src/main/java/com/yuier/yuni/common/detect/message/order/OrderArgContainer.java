package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.detector.message.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Title: OrderArgContainer
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.order
 * @Date 2024/11/10 0:59
 * @description: 指令参数集合
 * @Detail
 * 指令参数可以分为必选参数、可选参数
 * 顾名思义，必选参数定义后，消息中必须包含该参数；可选参数定义后，消息中可以不包含该参数
 * 在匹配消息时，会将所有必选参数匹配结束，才进入可选参数的匹配。定义指令时需要注意这一点。
 */

@Data
@AllArgsConstructor
public class OrderArgContainer implements OrderElement {

    /**
     * 必选参数的集合
     */
    private ArrayList<RequiredArg> requiredArgList;

    /**
     * 可选参数的集合
     */
    private ArrayList<OptionalArg> optionalArgList;

    public OrderArgContainer() {
        requiredArgList = new ArrayList<>();
        optionalArgList = new ArrayList<>();
    }

    /**
     * 检查当前集合中是否已经存在待添加的参数名
     * @param argName 待检查的参数名
     * @return 参数名是否已存在
     */
    private Boolean argNameExists(String argName) {
        for (RequiredArg arg : requiredArgList) {
            if (arg.getName().equals(argName)) {
                return true;
            }
        }
        for (OptionalArg arg : optionalArgList) {
            if (arg.getName().equals(argName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查待添加的参数名是否合法
     * @param argName 待检查的参数名
     */
    private void checkNameValid(String argName) {
        if (null == argName || argName.isEmpty()) {
            throw new RuntimeException("参数名不存在或为空！");
        }
        if (argNameExists(argName)) {
            throw new RuntimeException("本指令中参数名称" + argName + "已存在！");
        }
    }

    // 各种各样添加参数的方法

    public void addRequiredArg(String argName) {
        checkNameValid(argName);
        requiredArgList.add(new RequiredArg(argName));
    }

    public void addRequiredArg(String argName, OrderArgAcceptType contentType) {
        checkNameValid(argName);
        requiredArgList.add(new RequiredArg(argName, contentType));
    }

    public void addRequiredArg(String argName, String helpInfo) {
        checkNameValid(argName);
        requiredArgList.add(new RequiredArg(argName, helpInfo));
    }

    public void addRequiredArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
        checkNameValid(argName);
        requiredArgList.add(new RequiredArg(argName, contentType, helpInfo));
    }

    public void addRequiredArg(RequiredArg arg) {
        requiredArgList.add(arg);
    }

    public void addOptionalArg(String argName) {
        checkNameValid(argName);
        optionalArgList.add(new OptionalArg(argName));
    }

    public void addOptionalArg(String argName, OrderArgAcceptType contentType) {
        checkNameValid(argName);
        optionalArgList.add(new OptionalArg(argName, contentType));
    }

    public void addOptionalArg(String argName, String helpInfo) {
        checkNameValid(argName);
        optionalArgList.add(new OptionalArg(argName, helpInfo));
    }

    public void addOptionalArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
        checkNameValid(argName);
        optionalArgList.add(new OptionalArg(argName, contentType, helpInfo));
    }

    public void addOptionalArg(OptionalArg arg) {
        optionalArgList.add(arg);
    }

    /**
     * 返回必需参数的数量
     * @return 必需参数数量
     */
    public Integer getRequiredArgNum() {
        return requiredArgList.size();
    }

    @Override
    public Boolean valid() {
        boolean flag = true;
        for (RequiredArg arg : requiredArgList) {
            if (!arg.valid()) {
                flag = false;
                break;
            }
        }
        for (OptionalArg arg : optionalArgList) {
            if (!arg.valid()) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
