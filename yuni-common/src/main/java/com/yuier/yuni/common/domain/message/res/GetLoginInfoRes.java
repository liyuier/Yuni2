package com.yuier.yuni.common.domain.message.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.message.res.data.GetLoginInfoResData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetLoginInfoRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/22 22:37
 * @description: 获取登录号信息相应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetLoginInfoRes {
    private String status;
    private Integer retcode;
    private String message;
    private String wording;
    private String echo;
    private GetLoginInfoResData data;
}
