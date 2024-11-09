package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.definer.OrderDetectorDefiner;
import com.yuier.yuni.common.detect.messagematchedout.OrderMatchedOut;
import com.yuier.yuni.common.domain.message.MessageEvent;
import com.yuier.yuni.common.interfaces.plugin.MessageCalledPlugin;
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
public class GenShinStart implements MessageCalledPlugin<OrderDetectorDefiner, OrderMatchedOut> {

    @Override
    public BotAction run(MessageEvent messageEvent, OrderMatchedOut matchedOut) {
        return null;
    }

    @Override
    public OrderDetectorDefiner detectorDefine() {
        return null;
    }
}
