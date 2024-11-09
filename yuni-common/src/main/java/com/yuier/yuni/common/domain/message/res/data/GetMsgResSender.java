package com.yuier.yuni.common.domain.message.res.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetMsgResSender
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/22 23:14
 * @description: 获取消息接口响应 sender 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMsgResSender {

    private Long userId;

    private String nickname;

    private String sex;

    private Integer age;

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
