package com.yuier.yuni.core.randosoru;

import com.yuier.yuni.core.randosoru.bot.BotManager;
import com.yuier.yuni.core.randosoru.perm.PermissionManager;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import com.yuier.yuni.core.randosoru.subscribe.SubscribeManager;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: Randosoru
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru
 * @Date 2024/11/8 23:49
 * @description: 兰德索尔，奇迹与魔法的国度
 */

@Component
@NoArgsConstructor
public class Randosoru {

    @Autowired
    BotManager botManager;
    @Autowired
    PluginManager pluginManager;
    @Autowired
    PermissionManager permissionManager;
    @Autowired
    SubscribeManager subscribeManager;

    // 初始化管理框架
    public void initialize() {
        // 初始化插件管理器
        pluginManager.initialize();
        // 初始化 bot 管理器
        botManager.initialize();
        // 初始化权限管理器
        permissionManager.initialize();
        // 初始化订阅管理器
        subscribeManager.initialize();
    }
}
