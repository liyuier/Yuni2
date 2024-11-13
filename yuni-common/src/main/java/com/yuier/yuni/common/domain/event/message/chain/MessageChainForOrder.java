package com.yuier.yuni.common.domain.event.message.chain;

import com.yuier.yuni.common.constants.SystemConstants;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ReplyData;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Title: MessageChainForOrder
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/13 0:06
 * @description: 供指令探测器使用的 message chain
 */

@Data
@AllArgsConstructor
public class MessageChainForOrder {
    private int curSegIndex;
    private ArrayList<MessageSeg> content;
    /**
     * 回复消息
     * 如果消息链中包含回复消息，此处用于暂存，辅助后续解析逻辑
     */
    private ReplyData replyData;

    public MessageChainForOrder() {
        curSegIndex = 0;
        content = new ArrayList<>();
    }

    public void addTextSeg(String text) {
        content.add(new TextSeg(
                new TextData(text)
        ));
    }

    public Boolean startWithTextData() {
        return content.get(SystemConstants.FIRST_INDEX).typeOf(MessageDataEnum.TEXT) &&
                !((TextSeg) content.get(SystemConstants.FIRST_INDEX)).getData().getText().trim().isEmpty();
    }

    public Boolean startWithReplyData() {
        return content.get(SystemConstants.FIRST_INDEX).typeOf(MessageDataEnum.REPLY);
    }
}
