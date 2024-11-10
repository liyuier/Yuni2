package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.detector.MessageDetector;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title: OrderDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.detect
 * @Date 2024/11/9 16:36
 * @description: 指令消息探测器
 * @Detail
 * 一条指令分为指令头 OrderHead、指令参数 OrderArgContainer、指令选项 OrderOptions。三者均实现指令段 OrderElement 接口
 * 指令头
 *   一条指令的入口，最佳实践中，每条指令的指令头是唯一的
 * 指令参数
 *   跟在指令头后面，可以携带用户输入的信息，分为必选参数与可选参数。指令可以不定义指令参数
 * 指令选项
 *   可以视作对指令参数的扩展，某种意义上是一条“微型指令”
 * 举例说明
 *   现在我要定义一条发色图的指令 /色图 {数量} {最高数量} -标签 <标签> -合并  对于上述指令
 *   /色图 就是指令头，探测到消息开头为该字符串，进入色图指令的解析逻辑
 *   {数量}, {最高数量} 均为可选参数。
 *      用户不输入这两个参数时，功能开发者可自行设置默认数量；
 *      用户仅输入一个参数，视作需要的数量；
 *      用户输入两个参数，视作需要两个参数之间的任意数量色图。
 *    -标签 <标签> 是指令选项。其中 -标签 是选项名称，<标签> 是选项参数，且为必选参数
 *      用户可以不输入 -标签 选项名称，但如果输入该选项名，后面必须跟上选项参数
 *    -合并 也是指令选项，该指令选项没有设定指令参数。输入该选项名，表示需要将色图合并在转发消息中发出
 * 更详细的说明见每个指令元素的实现代码
 */

@Data
@Component
@AllArgsConstructor
public class OrderDetector implements MessageDetector {

    private OrderDetector() {}

    /**
     * 指令头
     */
    private OrderHead head;

    /**
     * 指令参数
     * 跟在指令头后面。可选参数必须在必选参数后面
     */
    private OrderArgContainer orderArgs;

    /**
     * 指令选项
     * 每个选项都是可选的，其结构分为选项标识与选项参数
     */
    private OrderOptionContainer orderOptions;

    public static class Builder {
        private OrderHead head;
        private OrderArgContainer args;
        private OrderOptionContainer options;

        public Builder setHead(String headName) {
            this.head = new OrderHead(headName);
            return this;
        }

        public Builder addRequiredArg(String name) {
            args.addRequiredArg(name);
            return this;
        }

        public Builder addRequiredArg(String argName, OrderArgAcceptType contentType) {
            args.addRequiredArg(argName, contentType);
            return this;
        }

        public Builder addRequiredArg(String argName, String helpInfo) {
            args.addRequiredArg(argName, helpInfo);
            return this;
        }

        public Builder addRequiredArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            args.addRequiredArg(argName, contentType, helpInfo);
            return this;
        }

        public Builder addRequiredArg(RequiredArg arg) {
            args.addRequiredArg(arg);
            return this;
        }

        public Builder addOptionalArg(String argName) {
            args.addOptionalArg(argName);
            return this;
        }

        public Builder addOptionalArg(String argName, OrderArgAcceptType contentType) {
            args.addOptionalArg(argName, contentType);
            return this;
        }

        public Builder addOptionalArg(String argName, String helpInfo) {
            args.addOptionalArg(argName, helpInfo);
            return this;
        }

        public Builder addOptionalArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            args.addOptionalArg(argName, contentType, helpInfo);
            return this;
        }

        public Builder addOptionalArg(OptionalArg arg) {
            args.addOptionalArg(arg);
            return this;
        }

        public Builder addOption(OrderOption option) {
            options.addOption(option);
            return this;
        }

        public OrderDetector build() {
            return new OrderDetector(this.head, this.args, this.options);
        }
    }

    @Override
    public Boolean hit() {
        return null;
    }

    @Override
    public Boolean defineValid() {
        return null;
    }
}
