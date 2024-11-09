package com.yuier.yuni.common.domain.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: OneBotMessageEventDto
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.dto
 * @Date 2024/4/10 1:06
 * @description: OneBot 上报消息事件 DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageEvent {

    // 收到消息的时间戳
    private Long time;

    // 收到消息的机器人QQ号
    private Long selfId;

    // 事件类型
    private String postType;

    /**
     * 消息类型。如果是私聊消息，为 private；如果是群聊消息，为group
     */
    private String messageType;

    /**
     * 消息子类型
     如果是私聊消息：如果是好友则是 friend，如果是群临时会话则是 group。
     如果是 群聊消息：正常消息是 normal，匿名消息是 anonymous，系统提示（如【管理员已禁止群内匿名聊天】）是 notice。
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
    private MessageChain message;

    /**
     * 原始消息内容
     */
    private String rawMessage;

    /**
     * 字体
     */
    private Long font;

    /**
     * 发送人信息
     */
    private MessageSender sender;

    /**
     * 群号。只有为群聊消息时有效。
     */
    private Long groupId;

    /**
     * 匿名信息，如果不是匿名消息则为 null
     */
    private AnonymousMessage anonymous;

    private MessageEventPosition messageEventPosition;

    // 以下为 LLOneBot 在 OneBot 消息事件标准之外添加的字段
    // 消息类型，是数组还是 CQ 码
    private String messageFormat;

    // 真实 ID 就是最真实的 ID
    // 其实这个字段在协议的 get_msg() 接口上会响应出来
    private Long realId;

    public boolean isPrivateMessage() {
        return messageType.equals(MessageTypeEnum.PRIVATE.toString());
    }

    public boolean isGroupMessage() {
        return messageType.equals(MessageTypeEnum.GROUP.toString());
    }

    public boolean isAnonymousMessage() {
        return null != anonymous;
    }

    public Long getPosition() {
        return this.isGroupMessage() ? groupId : userId;
    }
}
