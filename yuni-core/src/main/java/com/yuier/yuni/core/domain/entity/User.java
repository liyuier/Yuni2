package com.yuier.yuni.core.domain.entity;

import com.yuier.yuni.common.enums.PermissionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: User
 * @Author yuier
 * @Package com.yuier.yuni.core.entity
 * @Date 2024/11/9 1:09
 * @description: 用户，应由 OneBot 标准中的 sender 扩展而来
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    // 以下字段为 Yuni2 扩展
    PermissionLevel permissionLevel = PermissionLevel.USER;
}
