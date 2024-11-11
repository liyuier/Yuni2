package com.yuier.yuni.common.interfaces.detector;

import com.yuier.yuni.common.domain.event.message.MessageEvent;

/**
 * @Title: MessageDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.detector
 * @Date 2024/11/9 16:29
 * @description: 消息探测器
 */
public interface MessageDetector extends EventDetector<MessageEvent> {
    Boolean defineValid();
}
