package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.res.data.GetFileResData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetFileRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res
 * @Date 2024/5/1 17:37
 * @description: 获取文件响应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetFileRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
    private GetFileResData data;
}
