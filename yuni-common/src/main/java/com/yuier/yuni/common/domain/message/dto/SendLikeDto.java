package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SendLikeDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/4/23 23:03
 * @description: 为好友点赞 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendLikeDto {
    private Long userId;
    // 点赞次数，默认为 1；每个好友每天最多 10 次
    private Integer times;

    public SendLikeDto(Long userId) {
        this.userId = userId;
        this.times = 1;
    }
}
