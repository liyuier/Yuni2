package com.yuier.yuni.core.domain.pojo.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: PythonUtilImageRes
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.pojo.response
 * @Date 2024/12/25 23:51
 * @description: 获取插件信息图片返回值的pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PythonUtilImageRes {
    String image;
}
