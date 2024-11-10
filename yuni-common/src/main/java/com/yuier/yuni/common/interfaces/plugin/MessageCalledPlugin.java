package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.domain.event.message.ReMessageEvent;
import com.yuier.yuni.common.interfaces.detector.MessageDetector;
import com.yuier.yuni.common.interfaces.messagematchedout.MessageMatchedOut;
import com.yuier.yuni.common.utils.BotAction;

/**
 * @Title: MessageCalledPlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2024/11/9 22:13
 * @description: 消息事件触发的插件接口
 */
public interface MessageCalledPlugin<T extends MessageDetector, S extends MessageMatchedOut> extends NegativePlugin<T>{
    // 插件入口
    BotAction run(ReMessageEvent messageEvent, S matchedOut);
}
