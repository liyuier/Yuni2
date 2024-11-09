package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: DeleteMsgDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/23 22:40
 * @description: 撤回消息 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeleteMsgDto {
    private Long messageId;
}
