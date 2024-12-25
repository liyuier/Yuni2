package com.yuier.yuni.core.domain.pojo.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Title: PluginsInfoPicPojo
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.pojo
 * @Date 2024/12/25 23:40
 * @description: 获取插件总览图片 pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PluginsInfoPicPojo {
    HashMap<Integer, PluginInfoPojo> pluginsInfo;
}
