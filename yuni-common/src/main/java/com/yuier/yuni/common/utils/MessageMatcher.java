package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.MessageChainForOrder;
import com.yuier.yuni.common.domain.event.message.chain.seg.AtSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.ReplySeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.onebotapi.data.GetMessageResData;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.enums.MessageDataEnum;

import static com.yuier.yuni.common.constants.SystemConstants.BLANK_SPACE;
import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * @Title: MessageMatcher
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/12 23:57
 * @description: 匹配消息事件
 */

public class MessageMatcher {

    public static Boolean matchMessage(MessageEvent event, YuniMessagePlugin plugin) {
        if (plugin.getDetector() instanceof OrderDetector) {
            return matchOrderMessage(event, plugin);
        }
        // TODO
        return null;
    }

    private static Boolean matchOrderMessage(MessageEvent event, YuniMessagePlugin plugin) {
        MessageChainForOrder chainForOrder = splitMessageForOrder(event.getMessageChain());
        // TODO
        return null;
    }

    /**
     * 将 MessageChain 解析为适合指令消息探测器使用的结构
     * 思路为将每个消息段单独提出
     * 对于文本消息段，如果存在空格，以空格为分割符，将文本消息段分为多段
     * @param chain 消息链
     * @return MessageChainForOrder
     */
    private static MessageChainForOrder splitMessageForOrder(MessageChain chain) {
        MessageChainForOrder chainForOrder = new MessageChainForOrder();
        // 遍历消息段
        for (MessageSeg messageSeg : chain.getContent()) {
            // 如果消息段为文本消息段
            if (messageSeg.typeOf(MessageDataEnum.TEXT)) {
                // 将文本消息以空格为分割符，拆分为多段
                String[] strArr = ((TextSeg) messageSeg).getData().getText().split(BLANK_SPACE);
                for (String str : strArr) {
                    if (!str.trim().isEmpty()) {
                        chainForOrder.addTextSeg(str);
                    }
                }
            } else {
                chainForOrder.getContent().add(messageSeg);
            }
        }
        /* 如果存在回复消息，进行额外处理
          1. 将头部的回复类消息拆下，存放起来
          2. 删除一个回复消息自带的 @ 消息
         */
        if (chainForOrder.startWithReplyData()) {
            // 将回复消息段拆下，存入 chainForOrder 的 replyData 字段中
            chainForOrder.setReplyData(((ReplySeg) chainForOrder.getContent().remove(FIRST_INDEX)).getData());
            // 获取回复的目标消息的发送者的 ID, 以供后续删除对应的 @ 消息
            Long userId = getReplayTargetSenderId(Long.valueOf(chainForOrder.getReplyData().getId()));
            // 遍历后续消息段
            for (MessageSeg messageSeg : chainForOrder.getContent()) {
                if (messageSeg.typeOf(MessageDataEnum.AT)) {
                    // 如果发现了与回复消息相匹配的 @ 消息
                    if (((AtSeg) messageSeg).getData().getQq().equals(String.valueOf(userId))) {
                        // 将此 @ 消息删除
                        chainForOrder.getContent().remove(messageSeg);
                        break;
                    }
                }
            }
        }
        return chainForOrder;
    }

    /**
     * 获取回复的目标消息的发送者的 ID
     * 因为回复消息一般都自带一个 @ 消息，需要将这个 @ 消息删除
     * @param replyTargetMessageId  回复的目标消息的 ID
     * @return  目标消息的发送者的 ID
     */
    private static Long getReplayTargetSenderId(Long replyTargetMessageId) {
        // TODO 这里应该先去数据库里查消息

        // 数据库里查不到，请求 OneBot API
        GetMessageResData messageData = BotAction.getMessage(replyTargetMessageId);
        return messageData.getSender().getUserId();
    }

}
