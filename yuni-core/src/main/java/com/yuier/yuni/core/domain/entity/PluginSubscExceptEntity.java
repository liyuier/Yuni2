package com.yuier.yuni.core.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (PluginSubscExcept)表实体类
 *
 * @author liyuier
 * @since 2024-11-28 00:50:16
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_plugin_subsc_except")
public class PluginSubscExceptEntity {
    
    /**
    * name
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
    * 位置，group 或 private
    */
    private String position;
    
    /**
    * 位置 name
    */
    private Long posId;
    
    /**
    * 插件 name
    */
    private String pluginId;
    
    /**
    * 订阅策略，0 表示不订阅；1 表示订阅
    */
    private Integer subscFlag;

    public PluginSubscExceptEntity(String position, Long posId, String pluginId, Integer subscFlag) {
        this.position = position;
        this.posId = posId;
        this.pluginId = pluginId;
        this.subscFlag = subscFlag;
    }
}
