package com.yuier.yuni.core.domain.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SayHelloToPythonPojo
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.pojo
 * @Date 2024/12/24 1:37
 * @description: 测试，调用 python 服务的 hello 接口
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SayHelloToPythonPojo {
    String hello;
}
