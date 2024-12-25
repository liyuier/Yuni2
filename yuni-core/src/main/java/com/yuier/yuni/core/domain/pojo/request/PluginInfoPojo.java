package com.yuier.yuni.core.domain.pojo.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: PluginInfoPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.onebotapi.pojo
 * @Date 2024/12/23 2:23
 * @description: 请求 python 工具类画插件信息图的中间结构
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PluginInfoPojo {
    Integer id;
    String name;
    Boolean ordered;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ordered);
    }
}
