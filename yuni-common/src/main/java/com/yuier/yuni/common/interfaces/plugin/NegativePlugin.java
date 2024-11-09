package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.interfaces.detector.definer.EventDetectorDefiner;

/**
 * @Title: NegativePlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2024/11/9 16:21
 * @description: 被动触发的插件
 */
public interface NegativePlugin<T extends EventDetectorDefiner> {

    T detectorDefine();
}
