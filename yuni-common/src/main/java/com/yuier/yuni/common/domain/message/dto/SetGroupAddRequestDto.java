package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SetGroupAddRequestDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/5/1 1:50
 * @description: 处理加群请求/邀请 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SetGroupAddRequestDto {
    // 加群的请求 flag （需从上报的数据中获取）
    private String flag;
    //  add 或 invite ，请求类型（需与上报消息中的 sub_type） 字段相符
    private String subType;
    private String type;

    // 是否同意请求 / 邀请。more true
    private boolean approve;
    // 拒绝理由（仅在拒绝时有效）
    private String reason;
}
