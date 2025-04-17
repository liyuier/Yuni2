package com.yuier.yuni.common.domain.event.message.chain;

import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ReplyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: MessageChainForOrder
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/13 0:06
 * @description: 供指令探测器使用的 message chain
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageChainForOrder extends MessageChain{

    /**
     * 辅助字段，用于在匹配指令时，标识当前匹配的消息段在整个 cfo 中的位置
     * 从 0 开始
     */
    private int curSegIndex;

    /**
     * 回复消息
     * 如果消息链中包含回复消息，此处用于暂存，辅助后续解析逻辑
     */
    private ReplyData replyData;

    public MessageChainForOrder() {
        super();
        curSegIndex = 0;
    }

    /**
     * 获取当前消息段指针指向的消息段
     * @return  当前消息段指针指向的消息段
     */
    public MessageSeg<?> getCurMessageSeg() {
        return getContent().get(curSegIndex);
    }

    /**
     * 消息段指针左移
     * @param step  左移步数
     */
    public void curSegIndexStepBackBy(Integer step) {
        curSegIndex -= step;
    }

    /**
     * 消息段指针右移
     * @param step  右移步数
     */
    public void curSegIndexStepForwardBy(Integer step) {
        curSegIndex += step;
    }

    /**
     * 消息段是否已经遍历完毕，即消息段指针是否越界
     * @return  消息段是否已经遍历完毕
     */
    public Boolean messageSegsMatchedEnd() {
        return curSegIndex >= getContent().size();
    }

    /**
     * @return  从当前消息段开始（含），还剩多少消息段未匹配
     */
    public Integer restMessageSegNum() {
        return getContent().size() - curSegIndex;
    }

    public Boolean storesReplyData() {
        return replyData == null;
    }

}
