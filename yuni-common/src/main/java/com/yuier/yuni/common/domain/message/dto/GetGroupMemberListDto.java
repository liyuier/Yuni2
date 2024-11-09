package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupMemberListDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/24 22:51
 * @description: 获取群成员列表 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupMemberListDto {
    private Long groupId;
}
