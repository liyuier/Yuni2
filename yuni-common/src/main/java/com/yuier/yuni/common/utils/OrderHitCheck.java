package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.event.message.MessageChain;
import com.yuier.yuni.common.domain.event.message.MessageChainForOrder;
import com.yuier.yuni.common.domain.event.message.MessageSeg;
import com.yuier.yuni.common.domain.event.message.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;

/**
 * @Title: OrderHitCheck
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/10 17:20
 * @description: 检查消息链是否命中了指令探测器相关方法工具类
 */

public class OrderHitCheck {

    /**
     * 为了方便检查消息是否命中了指令探测器，需要对消息进行预处理
     * 具体思路为：
     *   1. 将消息段 ReMessageSeg 作为独立单位拆出保存、
     *   2. 对于文本消息，如果字符串中存在空格，以空格为分割符，将一个 text 类型的消息段拆分出多个 text 类型的消息段
     *   3. 对于包含回复类型消息段的消息链
     *     QQ 发出回复消息时，默认携带一个目标为 对象消息发出者 的 @ 消息段. 此消息段会干扰探测
     *     a) 将头部的回复类消息拆下，存放起来
     *     b) 删除 1 个回复消息自带的 @ 消息段
     * @param chain OneBot 解析客户端上报的消息事件得到的消息链
     * @return 方便检查是否命中指令探测器的结构
     */
    public static MessageChainForOrder splitMessageChainForOrder(MessageChain chain) {
        MessageChainForOrder chainForOrder = new MessageChainForOrder();
        // 遍历消息链中的消息段
        for (MessageSeg messageSeg : chain.getContent()) {
            if (messageSeg.typeOf(MessageDataEnum.TEXT)) {
                // 如果消息段为文本类型，以空格为分割符，将其拆分为多个文本类型消息段
                String[] strArr = ((TextData) messageSeg.getData()).getText().split(" ");
                for (String str : strArr) {
                    if (!str.trim().isEmpty()) {
                        chainForOrder.addTextSeg(str);
                    }
                }
            } else {
                chainForOrder.getContent().add(messageSeg);
            }
        }
        // TODO 对回复消息的特殊处理还没实现
        return chainForOrder;
    }
}
