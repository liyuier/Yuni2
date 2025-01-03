package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.matchedout.order.OrderMatchedOut;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.seg.ImageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import com.yuier.yuni.core.domain.pojo.request.BALogoPojo;
import com.yuier.yuni.core.domain.pojo.response.PythonUtilImageRes;
import com.yuier.yuni.core.util.CallPythonServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: BALogo
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2025/1/4 3:12
 * @description: 生成 BA 风格 logo
 */

@Component
@Plugin(name = "BA 风格图片")
public class BALogo implements MessagePluginBean<OrderDetector> {

    @Autowired
    CallPythonServiceUtil pyServCaller;

    private final String TEXT_LEFT = "text_left";
    private final String TEXT_RIGHT = "text_right";

    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {
        OrderMatchedOut orderMatchedOut = detector.getOrderMatchedOut();
        BALogoPojo baLogoPojo = new BALogoPojo(
                orderMatchedOut.getArgByName(TEXT_LEFT).asText(),
                orderMatchedOut.getArgByName(TEXT_RIGHT).asText()
        );
        // 请求 python 服务
        PythonUtilImageRes pluginsInfo = pyServCaller.getBaLogoPojo(baLogoPojo);
        // 发送图片
        BotAction.sendMessage(
                event.getPosition(),
                new MessageSeg<?>[] {
                        new ImageSeg(pluginsInfo.getImage())
                }
        );
    }

    @Override
    public OrderDetector detector() {
        return new OrderDetector.Builder()
                .setHead("ba")
                .addRequiredArg(TEXT_LEFT, OrderArgAcceptType.TEXT)
                .addRequiredArg(TEXT_RIGHT, OrderArgAcceptType.TEXT)
                .build();
    }

    @Override
    public String helpInfo() {
        return """
                使用指令 /ba {左侧文本} {右侧文本} ，生成 BA (Blue Archive) 风格 logo 图片
                如：/ba Genshin Impact""";
    }
}
