package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SetFriendAddRequestDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/23 23:20
 * @description: 处理加好友请求 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SetFriendAddRequestDto {
    // 加好友请求的 flag（需从上报的数据中获取）
    private String flag;
    // 是否同意请求
    private Boolean approve;
    // 添加后的好友备注（仅在同意时有效）
    private String remark;

    public SetFriendAddRequestDto(String flag, boolean approve) {
        this.flag = flag;
        this.approve = approve;
        this.remark = "";
    }
}
