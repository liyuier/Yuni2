package com.yuier.yuni.common.domain.event.messagesent;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: MessageSentEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.messagesent
 * @Date 2025/4/16 22:19
 * @description: BOT 发送消息事件
 * 参考 <a href="https://docs.go-cqhttp.org/event/#%E6%89%80%E6%9C%89%E4%B8%8A%E6%8A%A5">...</a>
 * message 与 message_sent 的数据是一致的, 区别仅在于后者是 bot 发出的消息.
 * 默认配置下不会上报 message_sent, 仅在配置 message 下 report-self-message 项为 true 时上报
 * <p>
 * 又参考 <a href="https://llonebot.com/zh-CN/develop/extends_api">...</a>
 * message_sent 事件的 target_id
 *   相比于 go-cq 多了个 target_id 字段表示发送的目标QQ号或者群号
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeDefine("message_sent")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "message_type",
        defaultImpl = com.yuier.yuni.common.domain.event.message.MessageEvent.class,
        visible = true)
public class MessageSentEvent<T extends MessageSender> extends OneBotEvent {
    /**
     * 消息类型。
     * - private 私聊消息
     * - group 群聊消息
     */
    private String messageType;

    /**
     * 消息子类型
     * 私聊消息
     *   - friend 好友会话
     *   - group 群临时会话
     *   - other 其他
     * 群聊消息
     *   - normal 正常消息
     *   - anonymous 匿名消息
     *   - notice 系统提示
     */
    private String subType;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 发送者 QQ 号
     */
    private Long userId;

    /**
     * 消息内容
     */
    private ArrayList<MessageSeg<?>> message;

    /**
     * 消息链
     */
    private MessageChain messageChain;

    /**
     * 原始消息内容
     */
    private String rawMessage;

    /**
     * 字体
     */
    private Long font;

    // 消息发送者
    private T sender;

    // 以下为 LLOneBot 在 OneBot 消息事件标准之外添加的字段
    // 消息类型，是数组还是 CQ 码
    private String messageFormat;

    // 真实 ID 就是最真实的 ID （划掉）
    // 其实这个字段在协议的 get_msg() 接口上会响应出来
    private Long realId;
}
