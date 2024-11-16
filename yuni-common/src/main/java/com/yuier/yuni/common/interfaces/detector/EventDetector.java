package com.yuier.yuni.common.interfaces.detector;

import com.yuier.yuni.common.domain.event.OneBotEvent;

/**
 * @Title: EventDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.detector
 * @Date 2024/11/9 16:23
 * @description: 事件探测器接口
 */
public interface EventDetector<T extends OneBotEvent> {

    /**
     * @return  检查消息是否命中消息探测器
     */
    Boolean hit(T event);
}
