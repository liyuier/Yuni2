package com.yuier.yuni.common.domain.event.message.sender;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @Title: MessageSenderDto
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.dto
 * @Date 2024/4/10 1:31
 * @description: 消息发送人类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSender {

    // 发送者 QQ 号
    private Long userId;

    // 昵称
    private String nickname;

    /**
     * 性别
     * - male
     * - female
     * - unknown
     */
    private String sex;

    // 年龄
    private int age;

}
