package com.yuier.yuni.core.plugins.inner;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import org.springframework.stereotype.Component;

/**
 * @Title: About
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins.inner
 * @Date 2025/1/2 1:41
 * @description: 关于
 */

@Component
@Plugin(name = "关于", inner = true)
public class About implements MessagePluginBean<OrderDetector> {

    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {
        BotAction.sendMessage(event.getPosition(), new MessageChain(
                """
                        后端服务：Yuni bot
                        作者：liyuier
                        仓库地址：https://github.com/liyuier/Yuni2"""
        ));
    }

    @Override
    public OrderDetector detector() {
        return new OrderDetector.Builder().setHead("关于").build();
    }

    @Override
    public String helpInfo() {
        return "使用 /关于 命令查看 About 信息";
    }
}
