package com.yuier.yuni.common.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long userId;

    private String nickname;

    private String sex;

    private int age;

    // 以下字段只有群聊消息有效

    // 群名片/备注
    private String card;

    // 地区
    private String area;

    // 成员等级
    private String level;

    // 角色，owner 或 admin 或 member
    private String role;

    // 专属头衔
    private String title;
}
