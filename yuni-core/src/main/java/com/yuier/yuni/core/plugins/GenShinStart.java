package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.enums.MessageTypeEnum;
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
@Plugin(name = "原神启动")
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
        return """
                用户使用 /原神 指令，机器人回复 “启动！”。
                作者君一般用这个插件来测试新功能，并不能真的启动原神（原神玩家可以停止幻想）。""";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessagePluginBean.super.listenAt();
    }
}
