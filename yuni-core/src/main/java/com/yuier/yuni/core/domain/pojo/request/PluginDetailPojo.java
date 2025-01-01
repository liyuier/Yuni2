package com.yuier.yuni.core.domain.pojo.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: PluginDetailPojo
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.pojo.request
 * @Date 2025/1/1 22:45
 * @description: 插件细节信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PluginDetailPojo {
    Integer id;
    String name;
    String help;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, help);
    }
}
