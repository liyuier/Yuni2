package com.yuier.yuni.common.domain.plugin;

import com.yuier.yuni.common.interfaces.detector.EventDetector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @Title: YuniNegativePlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.plugin
 * @Date 2024/11/11 23:17
 * @description: 被动插件定义类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YuniNegativePlugin extends YuniPlugin {

    // 插件入口
    private Method runMethod;

    // 插件注册的消息探测器
    private EventDetector<?> detector;
}
