package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: NoDataRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res
 * @Date 2024/4/23 22:43
 * @description: 没有响应数据的返回体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NoDataRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
}
