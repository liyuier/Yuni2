package com.yuier.yuni.common.domain.plugin;

import com.yuier.yuni.common.enums.SubmitConditions;
import com.yuier.yuni.common.interfaces.plugin.PluginBean;
import lombok.Data;

/**
 * @Title: YuniPlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.plugin
 * @Date 2024/11/11 23:48
 * @description: 还是得要一个插件的顶层父类
 */

@Data
public class YuniPlugin {

    // 插件 ID
    private String id;

    // 插件 Bean 本体
    private PluginBean pluginBean;

    // 插件帮助信息
    private String helpInfo;

    // 作为新插件，第一次拉起时，希望机器人实例默认订阅还是不订阅
    private SubmitConditions submitCondition;

    // 插件是否活跃
    private Boolean isSubscribed;
}
