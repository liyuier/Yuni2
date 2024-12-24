package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
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
@Plugin(permission = PermissionLevel.MASTER, name = "原神启动")
public class GenShinStart implements MessagePluginBean<OrderDetector> {

    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {
        System.out.println("进入插件 GenShinStart");
        BotAction.sendMessage(event.getPosition(), new MessageChain("启动！"));
    }

    @Override
    public OrderDetector detector() {
        return new OrderDetector.Builder().setHead("原神").build();
    }

    @Override
    public String helpInfo() {
        return "原神，启动！";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessagePluginBean.super.listenAt();
    }
}
