package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.res.data.GetGroupInfoResData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Title: GetGroupListRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res
 * @Date 2024/4/23 23:36
 * @description: 获取群列表响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupListRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
    private List<GetGroupInfoResData> data;
}
