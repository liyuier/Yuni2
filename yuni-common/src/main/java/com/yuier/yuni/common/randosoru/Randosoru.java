package com.yuier.yuni.common.randosoru;

import com.yuier.yuni.common.randosoru.bot.BotManager;
import com.yuier.yuni.common.randosoru.plugin.PluginManager;
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

    // 初始化管理框架
    public void initialize() {
        botManager.initialize();
        pluginManager.initialize();
    }
}
