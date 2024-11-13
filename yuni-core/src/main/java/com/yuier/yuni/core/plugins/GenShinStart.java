package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.interfaces.plugin.MessageCalledPluginBean;
import com.yuier.yuni.common.utils.BotAction;
import org.springframework.stereotype.Component;

/**
 * @Title: GenShinStart
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2024/11/9 1:53
 * @description: 原神，启动！
 */

@Component
@Plugin
public class GenShinStart implements MessageCalledPluginBean<OrderDetector> {

    @Override
    public BotAction run(MessageEvent event, OrderDetector detector) {
        return null;
    }

    @Override
    public OrderDetector detector() {
        return null;
    }

    @Override
    public String helpInfo() {
        return "原神，启动！";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessageCalledPluginBean.super.listenAt();
    }
}
