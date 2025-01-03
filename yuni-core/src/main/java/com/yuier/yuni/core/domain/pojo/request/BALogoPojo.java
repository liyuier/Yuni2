package com.yuier.yuni.core.domain.pojo.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: BALogoPojo
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.pojo.request
 * @Date 2025/1/4 3:18
 * @description: ba 风格图片 pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BALogoPojo {
    String textl;
    String textr;
}
