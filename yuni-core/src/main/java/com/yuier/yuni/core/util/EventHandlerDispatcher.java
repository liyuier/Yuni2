package com.yuier.yuni.core.util;

import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import com.yuier.yuni.core.randosoru.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.yuier.yuni.common.constants.SystemConstants.LOCAL_BOT;

/**
 * @Title: EventHandlerDispatcher
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2024/11/11 22:40
 * @description: 事件分发器
 */

@Component
public class EventHandlerDispatcher {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BotManager botManager;

    /**
     * 收到事件请求后，在业务逻辑之前进行一些前期处理
     * 原则上不对 event 做任何修改
     * @param event 事件请求
     */
    public void eventPreHandle(OneBotEvent event) {
        // 在 ThreadLocalUtil 中设置当前请求对应的 bot
        setCurBot(event);
    }

    /**
     * 每个上报事件都应当由发出该上报事件的 OneBot 实现所对应的 bot 实例对应  ~~牙白，这样的话以后就只能对接 OneBot 客户端了~~
     * 收到上报事件时，在请求生命周期内，在 ThreadLocal 中设置当前 bot
     */
    private void setCurBot(OneBotEvent event) {
        // 将当前请求对应的 bot 的 ID 放入 ThreadLocal 中
        HashMap<String, Object> localInfo = new HashMap<>();
        localInfo.put(LOCAL_BOT, botManager.getBotById(event.getSelfId()));
        ThreadLocalUtil.set(localInfo);
    }
}
