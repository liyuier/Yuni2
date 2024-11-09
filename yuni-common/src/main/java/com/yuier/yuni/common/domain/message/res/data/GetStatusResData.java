package com.yuier.yuni.common.domain.message.res.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetStatusResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/5/1 2:29
 * @description: 获取运行状态响应 data 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetStatusResData {
    // 当前 QQ 是否在线
    private Boolean online;
    // 状态是否符合预期
    private Boolean good;
}
