package com.yuier.yuni.common.interfaces.detector.message;

import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.interfaces.detector.EventDetector;

/**
 * @Title: MessageDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.detector
 * @Date 2024/11/9 16:29
 * @description: 消息探测器
 */

public interface MessageDetector extends EventDetector<MessageEvent<?>> {

    /**
     * @return  当前探测器的定义是否合法
     */
    Boolean defineValid();
}
