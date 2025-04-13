package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.interfaces.detector.message.order.OrderElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

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

    // 对于当前消息，本选项是否已经与某段消息匹配上
    private Boolean matched = false;

    public OrderOption() {
        optionArgs = new OrderArgContainer();
    }

    /**
     * @return  要匹配上当前选项，最少需要多少消息段
     *          参考 OrderDetector.orderLeastSegNum
     */
    public Integer leastMessageSegNum() {
        return 1 + optionArgs.getRequiredArgNum();
    }

    public ArrayList<RequiredArg> getRequiredArgs() {
        return optionArgs.getRequiredArgList();
    }

    public ArrayList<OptionalArg> getOptionalArgs() {
        return optionArgs.getOptionalArgList();
    }

    @Override
    public Boolean valid() {
        return null != name && !name.isEmpty()
                && null != flag && optionArgs.valid();
    }
}
