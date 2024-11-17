package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.detect.message.order.OrderOptionContainer;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import org.springframework.stereotype.Component;

/**
 * @Title: OrderTest
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2024/11/17 21:37
 * @description: 指令测试
 */

@Component
@Plugin
public class OrderTest implements MessagePluginBean<OrderDetector> {

    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {
        /*
         * （建议使用 IDE 的分页功能，将本注释与 detector 方法代码并行查看）
         * 测试用例，在 QQ 中使用用户账号手动测试最方便
         * * 测试用例解释：
         * * [<type>content] 为一个典型的测试用例消息段，其中 <type> 表示该消息段类型，content 表示消息段的关键属性。实际使用中，如果两个相邻消息段均为文本消息段，需要使用空格分开。举例：
         * * [<image>https://www.baidu.com/img/flexible/logo/pc/peak-result.png] 表示一个图片消息段，其 url 为圆括号中内容
         * * [<at>123456] 表示一个 @ 消息段，@ 的目标用户账号为 123456
         * * 其余不再解释。
         * 一、参数部分验证
         * （一）命中
         * 1. [<text>/test][<text>1][<at>123456][<text>2]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3
         * 2. [<text>/test][<text>1][<at>123456][<text>2][<image>https://path/to/pic.png]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、OA1
         * （二）不命中
         * 1. [<text>/test][<text>1][<text>2]  -- 缺少 RA2
         * 2. [<text>/test][<text>1][<at>123456]  -- 缺少 RA3
         * 3. [<text>/test][<text>1][<text>2][<at>123456]  -- RA2 与 RA3 顺序不对
         * 4. [<text>/test][<at>123456][<text>2]  -- 缺少 RA1
         * 5. [<text>/test][<text>1][<at>123456][<text>qwer]  -- RA3 类型不对
         * 6. [<text>/test][<text>1][<at>123456][<text>2][<text>123]  -- OA1 类型不对
         *
         * 二、选项部分验证
         * （一）命中
         * 1. [<text>/test][<text>1][<at>123456][<text>2][<text>-2]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、O2
         * 2. [<text>/test][<text>1][<at>123456][<text>2][<text>-2][<text>-1][<text>some_text]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、O2、O1(ORA1)
         * 3. [<text>/test][<text>1][<at>123456][<text>2][<text>-2][<text>O_OA_1][<text>-1][<text>some_text][<text>123]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、O2(OO1)、O1(ORA1、OOA1)
         * 4. [<text>/test][<text>1][<at>123456][<text>2][<text>-2][<text>O_OA_1][<image>example.jpg][<text>-1][<text>some_text][<text>123]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、O2(OO1、OO2)、O1(ORA1、OOA1)
         * 5. [<text>/test][<text>1][<at>123456][<text>2][<text>-2][<image>example.jpg][<text>-1][<text>some_text][<text>123]
         *    -- 该用例命中了 指令头、RA1、RA2、RA3、O2(OO2)、O1(ORA1、OOA1)
         * 更复杂的示例请自行编写，参考 OrderDetector.java 下 matchOptionalArgs 下注释
         * （二）不命中
         * 懒得写了
         */
        return;
    }

    @Override
    public OrderDetector detector() {
        return new OrderDetector.Builder()
                .setHead("~~test")
                .addRequiredArg("RA1", OrderArgAcceptType.TEXT)
                .addRequiredArg("RA2", OrderArgAcceptType.AT)
                .addRequiredArg("RA3", OrderArgAcceptType.NUMBER)
                .addOptionalArg("OA1", OrderArgAcceptType.IMAGE)
                .addOption(
                        new OrderOptionContainer.OptionBuilder()
                                .setNameAndFlag("O1", "-1")
                                .addRequiredArg("ORA1")
                                .addOptionalArg("OOA1", OrderArgAcceptType.NUMBER)
                                .addOptionalArg("OOA2")
                                .build()
                )
                .addOption(
                        new OrderOptionContainer.OptionBuilder()
                                .setNameAndFlag("O2", "-2")
                                .addOptionalArg("OO1",OrderArgAcceptType.TEXT)
                                .addOptionalArg("OO2", OrderArgAcceptType.IMAGE)
                                .addOptionalArg("OO3")
                                .build()
                )
                .build();
    }

    @Override
    public String helpInfo() {
        return "指令测试";
    }
}
