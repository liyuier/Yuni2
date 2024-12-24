package com.yuier.yuni.common.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @Title: GetPluginsInfoPicPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.onebotapi.pojo
 * @Date 2024/12/23 2:23
 * @description: 请求 python 工具类画插件信息图的中间结构
 */

@Data
@AllArgsConstructor
public class GetPluginsInfoPicPojo {
    Integer id;
    String name;
    Boolean ordered;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ordered);
    }
}
