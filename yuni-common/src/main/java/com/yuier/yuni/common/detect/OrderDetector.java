package com.yuier.yuni.common.detect;

import com.yuier.yuni.common.interfaces.detector.MessageDetector;

/**
 * @Title: OrderDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.detect
 * @Date 2024/11/9 16:36
 * @description: 指令消息探测器
 */
public class OrderDetector implements MessageDetector {
    @Override
    public void build() {

    }

    @Override
    public Boolean hit() {

        return true;
    }
}
