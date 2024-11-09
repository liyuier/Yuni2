package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SetGroupLeaveDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/5/1 1:56
 * @description: 推出群组 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SetGroupLeaveDto {
    private Long groupId;
    /**
     * 是否解散
     * 如果登录号是群主，则仅在此项为 true 时能够解散
     */
    private boolean isDismiss;

    private SetGroupLeaveDto(Long groupId) {
        this.groupId = groupId;
        this.isDismiss = false;
    }
}
