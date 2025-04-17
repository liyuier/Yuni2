package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.interfaces.detector.EventDetector;

/**
 * @Title: NegativePluginBean
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2024/11/9 16:21
 * @description: 被动触发的插件
 */
public interface NegativePluginBean<T extends OneBotEvent, S extends EventDetector<?>> extends PluginBean {


    /**
     * 插件入口
     * @param event 触发该插件的 OneBot 上报事件
     * @param detector 该被动插件的事件探测器
     */
    void run(T event, S detector);

    /**
     * 消息探测器定义方法，内部应直接产生一个事件探测器
     * @return 返回一个可供使用的事件探测器
     */
    S detector();
}
