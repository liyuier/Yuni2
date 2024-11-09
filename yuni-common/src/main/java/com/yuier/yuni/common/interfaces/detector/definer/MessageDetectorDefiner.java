package com.yuier.yuni.common.interfaces.detector.definer;


/**
 * @Title: MessageDetectorDefiner
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.detector.definer
 * @Date 2024/11/9 18:32
 * @description: 消息探测器接口
 */
public interface MessageDetectorDefiner extends EventDetectorDefiner {
    Boolean defineValid();
}
