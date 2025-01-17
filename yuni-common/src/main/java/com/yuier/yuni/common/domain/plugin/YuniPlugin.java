package com.yuier.yuni.common.domain.plugin;

import com.yuier.yuni.common.enums.SubscribeCondition;
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

    // 插件数字 ID
    private Integer id;

    // 插件 name
    private String name;

    // 插件 Bean 本体
    private PluginBean pluginBean;

    // 插件帮助信息
    private String helpInfo;

    // 作为新插件，第一次拉起时，希望机器人实例默认订阅还是不订阅
    private SubscribeCondition submitCondition;

    // 插件是否活跃
    private Boolean isSubscribed;

    // 是否内置插件；内置插件无法退订
    private Boolean inner;
}
