package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.domain.message.MessageEvent;
import com.yuier.yuni.common.interfaces.messagematchedout.MessageMatchedOut;
import com.yuier.yuni.common.interfaces.detector.definer.MessageDetectorDefiner;
import com.yuier.yuni.common.utils.BotAction;

/**
 * @Title: MessageCalledPlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2024/11/9 22:13
 * @description: 消息事件触发的插件接口
 */
public interface MessageCalledPlugin<T extends MessageDetectorDefiner, S extends MessageMatchedOut> extends NegativePlugin<T>{
    // 插件入口
    BotAction run(MessageEvent messageEvent, S matchedOut);
}
