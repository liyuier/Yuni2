package com.yuier.yuni.common.domain.event.message;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.sender.GroupMessageSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: GroupMessageEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message
 * @Date 2024/11/10 21:55
 * @description: 群聊消息事件
 * 相比私聊消息，群聊消息多了 groupId 和 anonymous 两个字段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeDefine("group")
public class GroupMessageEvent extends ReMessageEvent{
    // 群号
    private Long groupId;

    // 匿名消息，如果不是匿名消息则为 null
    private AnonymousMessage anonymous;

    // 发送者
    private GroupMessageSender sender;
}
