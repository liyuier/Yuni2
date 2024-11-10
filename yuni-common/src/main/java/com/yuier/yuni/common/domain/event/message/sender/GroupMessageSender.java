package com.yuier.yuni.common.domain.event.message.sender;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Title: GroupMessageSender
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.sender
 * @Date 2024/11/10 21:41
 * @description: 群消息事件的 sender
 * 继承 MessageSender
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupMessageSender extends MessageSender {

    // 群名片/备注
    private String card;

    // 地区
    private String area;

    // 成员等级
    private String level;

    /**
     * 角色
     * - owner
     * - admin
     * - member
     */
    private String role;

    // 专属头衔
    private String title;

}
