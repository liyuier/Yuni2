package com.yuier.yuni.core.randosoru.bot;

import com.yuier.yuni.common.domain.bot.BotsConfig;
import com.yuier.yuni.common.domain.bot.YuniBot;
import com.yuier.yuni.common.domain.onebotapi.data.GetLoginInfoResData;
import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.utils.BotAction;
import com.yuier.yuni.core.util.YamlParser;
import com.yuier.yuni.core.domain.entity.User;
import com.yuier.yuni.core.randosoru.bot.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${bots.config}")
    String botsConfigFile;

    private HashMap<Long, YuniBot> botMap;
    private HashMap<Long, User> userMap;

    public BotManager() {
        botMap = new HashMap<>();
    }

    // 初始化
    public void initialize() {
        // 加载配置文件中的 Bot 定义
        loadBots();
        userManager.initialize();
    }

    public YuniBot getBotById(Long botId) {
        return botMap.getOrDefault(botId, null);
    }

    /**
     * 加载配置文件中的 bot 到内存中
     */
    private void loadBots() {
        Resource resource = resourceLoader.getResource("classpath:" + botsConfigFile);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BotsConfig botsConfig = YamlParser.parse(inputStream, BotsConfig.class);
        List<YuniBot> bots = botsConfig.getBots();
        for (YuniBot bot : bots) {
            GetLoginInfoResData loginInfo = BotAction.getLoginInfo(bot.getOnebotUrl());
            if (null != loginInfo) {
                bot.setId(loginInfo.getUserId());
                /*
                 * 如果配置中给出了 bot 昵称，那么直接使用配置的昵称
                 * 否则使用 bot 账号的昵称
                 */
                bot.setNickName(
                        bot.getNickName() != null ? bot.getNickName() :
                                loginInfo.getNickname()
                );
                botMap.put(bot.getId(), bot);
            }
        }
    }
}
