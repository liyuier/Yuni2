package com.yuier.yuni.common.detect.definer;

import com.yuier.yuni.common.interfaces.detector.definer.EventDetectorDefiner;
import com.yuier.yuni.common.interfaces.detector.definer.MessageDetectorDefiner;

/**
 * @Title: OrderDetectorDefiner
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.definer
 * @Date 2024/11/9 16:38
 * @description: 指令消息探测器定义器
 */
public class OrderDetectorDefiner implements MessageDetectorDefiner {


    @Override
    public EventDetectorDefiner build() {
        return null;
    }

    @Override
    public Boolean defineValid() {
        return null;
    }
}
