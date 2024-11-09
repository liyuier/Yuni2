package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.res.data.GetGroupInfoResData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupInfoRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res
 * @Date 2024/4/23 23:33
 * @description: 获取群信息接口响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupInfoRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
    private GetGroupInfoResData data;
}
