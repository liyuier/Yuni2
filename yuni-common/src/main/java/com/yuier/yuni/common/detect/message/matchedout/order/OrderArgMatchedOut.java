package com.yuier.yuni.common.detect.message.matchedout.order;

import com.yuier.yuni.common.domain.event.message.chain.seg.data.*;
import com.yuier.yuni.common.enums.OrderArgAcceptType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: OrderArgMatchedOut
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.matchedout
 * @Date 2024/11/15 0:15
 * @description: 消息命中后，指令探测器解析出来的指令参数
 * TODO  也许还有更好的处理方法。。。
 */

@Data
@AllArgsConstructor
public class OrderArgMatchedOut {

    // 参数名
    private String name;

    // 参数类型
    private OrderArgAcceptType contentType;

    // 另一种实现方式。。。
    private MessageData messageData;

    // 参数帮助信息
    private String helpInfo;

    public OrderArgMatchedOut() {
        name = "";
        contentType = OrderArgAcceptType.TEXT;
        helpInfo = "";
    }

    public String asText() {
        return ((TextData) messageData).getText();
    }

    public Long asLong() {
        return Long.parseLong(((TextData) messageData).getText());
    }

    public Integer asInteger() {
        return Integer.parseInt(((TextData) messageData).getText());
    }

    public AtData asAt() {
        return ((AtData) messageData);
    }

    public ImageData asImage() {
        return ((ImageData) messageData);
    }

    public String asUrl() {
        return ((TextData) messageData).getText();
    }

    public ReplyData asReply() {
        return ((ReplyData) messageData);
    }
}
