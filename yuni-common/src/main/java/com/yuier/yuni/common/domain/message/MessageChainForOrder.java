package com.yuier.yuni.common.domain.message;

import com.yuier.yuni.common.constants.SystemConstants;
import com.yuier.yuni.common.domain.message.data.ReplyData;
import com.yuier.yuni.common.domain.message.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Title: MessageChainForOrderDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message
 * @Date 2024/5/12 23:29
 * @description: 用于指令探测的消息链
 */
@Data
@AllArgsConstructor
public class MessageChainForOrder {
    private int curSegIndex;
    private ArrayList<MessageSeg> content;
    private ReplyData replyData;

    public MessageChainForOrder() {
        curSegIndex = 0;
        content = new ArrayList<>();
    }

    public void addTextSeg(String text) {
        content.add(new MessageSeg(
                MessageDataEnum.TEXT.toString(),
                new TextData(text)
        ));
    }

    public Boolean startWithTextData() {
        return content.get(SystemConstants.FIRST_INDEX).typeOf(MessageDataEnum.TEXT) &&
                !((TextData) content.get(SystemConstants.FIRST_INDEX).getData()).getText().trim().isEmpty();
    }

    public Boolean startWithReplyData() {
        return content.get(SystemConstants.FIRST_INDEX).typeOf(MessageDataEnum.REPLY);
    }
}
