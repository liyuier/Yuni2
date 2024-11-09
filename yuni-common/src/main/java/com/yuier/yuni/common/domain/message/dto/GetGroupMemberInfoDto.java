package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupMemberInfoDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/24 22:52
 * @description: 获取群成员信息 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupMemberInfoDto {
    private Long groupId;
    private Long userId;
    // 是否不使用缓存，默认 false
    private Boolean noCache;

    public GetGroupMemberInfoDto(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
        this.noCache = false;
    }
}
