package com.yuier.yuni.core.randosoru.plugin;

import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.detect.message.pattern.PatternDetector;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.PrivateMessageEvent;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.utils.BeanCopyUtils;

/**
 * @Title: MessageMatcher
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/12 23:57
 * @description: 匹配消息事件
 */

public class MessageMatcher {

    /**
     * 检查消息能否匹配模式探测器
     * @param event  消息
     * @param plugin  注册了模式探测器的插件
     * @return  消息是否命中插件
     */
    public static Boolean matchPatternDetector(MessageEvent<?> event, YuniMessagePlugin plugin) {
        // 检查 listener
        if (!checkListener(event, plugin)) {
            return false;
        }
        PatternDetector detector = (PatternDetector) plugin.getDetector();
        return detector.hit(event);
    }

    /**
     * 检查消息能否匹配指令探测器
     * @param event  消息
     * @param plugin  注册了指令探测器的插件
     * @return  消息是否命中插件
     */
    public static Boolean matchOrderMessage(MessageEvent<?> event, YuniMessagePlugin plugin) {
        // 检查 listener
        if (!checkListener(event, plugin)) {
            return false;
        }
        OrderDetector detector = (OrderDetector) plugin.getDetector();
        return detector.hit(event);
    }

    /**
     * 检查插件是否监听当前消息类型
     * @param event  消息事件
     * @param plugin  插件
     * @return  插件是否监听当前消息类型
     */
    private static Boolean checkListener(MessageEvent<?> event, YuniMessagePlugin plugin) {
        MessageTypeEnum listener = plugin.getListener();
        switch (listener) {
            case ALL -> {
                return true;
            }
            case GROUP -> {
                return event instanceof GroupMessageEvent;
            }
            case PRIVATE -> {
                return event instanceof PrivateMessageEvent;
            }
            default -> {
                return false;
            }
        }
    }

}
