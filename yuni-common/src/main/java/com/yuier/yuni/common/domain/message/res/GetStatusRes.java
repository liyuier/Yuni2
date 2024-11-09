package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.res.data.GetStatusResData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetStatusRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res
 * @Date 2024/5/1 2:31
 * @description: 获取运行状态响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetStatusRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
    private GetStatusResData data;
}
