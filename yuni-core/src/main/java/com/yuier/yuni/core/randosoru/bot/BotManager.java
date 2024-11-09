package com.yuier.yuni.core.randosoru.bot;

import com.yuier.yuni.core.randosoru.bot.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: BotManager
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.princess
 * @Date 2024/11/9 0:32
 * @description: 机器人管理容器
 */

@Component
public class BotManager {

    @Autowired
    UserManager userManager;

    // 初始化
    public void initialize() {

        userManager.initialize();
    }
}
