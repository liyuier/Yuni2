package com.yuier.yuni.common.domain.message.res.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupMemberInfoResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/24 22:57
 * @description: 获取群成员信息响应 data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupMemberInfoResData {
    private Long groupId;
    private Long userId;
    private String nickname;
    // 群名片 / 备注
    private String card;
    private String sex;
    private Integer age;
    private String area;
    // 加群时间戳
    private Long joinTime;
    // 最后发言时间戳
    private Long lastSendTime;
    // 成员等级
    private String level;
    private String role;
    // 是否不良记录成员
    private boolean unfriendly;
    // 专属头衔
    private String title;
    // 专属头衔过期时间戳
    private Long titleExpiredTime;
    // 是否允许修改群名片
    private boolean cardChangeable;

    // llOneBot 自己添加的字段
    private Integer qqLevel;
    private Boolean isRobot;
    private Long shutUpTimestamp;
}
