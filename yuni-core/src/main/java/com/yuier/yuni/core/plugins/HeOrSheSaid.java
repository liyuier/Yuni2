package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.pattern.PatternDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.sender.GroupMessageSender;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Title: HeOrSheSaid
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2025/1/2 22:52
 * @description: 穿山甲说了什么？
 */

@Component
@Plugin(name = "穿山甲说了什么？")
public class HeOrSheSaid implements MessagePluginBean<PatternDetector> {
    @Override
    public void run(MessageEvent<?> event, PatternDetector detector) {
        // 生成 [min, max] 范围内的随机 int（包含上下界）
        int min = 1;
        int max = 100;
        int randomInt = ThreadLocalRandom.current().nextInt(min, max + 1);
        if (randomInt > 20) {
            return;
        }
        MessageChain messageChain = event.getMessageChain();
        MessageSender sender = event.getSender();
        String groupCard = ((GroupMessageSender) sender).getCard();
        String senderName = StringUtils.hasText(groupCard) ?
                        groupCard : sender.getNickname();
        messageChain.replace("我", senderName);
        BotAction.sendMessage(event.getPosition(), messageChain);

    }

    @Override
    public PatternDetector detector() {
        return new PatternDetector(
                chain -> chain.contains("我")
        );
    }

    @Override
    public String helpInfo() {
        return """
                以 20% 的概率复读带“我”字的消息。
                复读时会将“我”字替换为发送者昵称。
                仅针对群聊消息。""";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessageTypeEnum.GROUP;
    }
}
