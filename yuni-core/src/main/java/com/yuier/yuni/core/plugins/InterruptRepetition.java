package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.pattern.PatternDetector;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.ImageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.enums.SubscribeCondition;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Title: InterruptRepetition
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2025/4/19 19:07
 * @description: 打断复读
 */

@Component
@Plugin(name = "打断复读！", subscribe = SubscribeCondition.NO)
public class InterruptRepetition implements MessagePluginBean<PatternDetector> {

    @Autowired
    ServletContext servletContext;

    HashMap<Long, Integer> groupLashMessageMap = new HashMap<>();
    HashMap<Long, Integer> groupSameMessageNumberMap = new HashMap<>();

    static final Integer INTERRUPT_THRESHOLD = 3;
    static final String DEFAULT_INTERRUPT_IMAGE_PATH = "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition.jpeg";

    @Override
    public void run(MessageEvent<?> event, PatternDetector detector) {
        GroupMessageEvent messageEvent = (GroupMessageEvent) event;
        if (messageEvent == null) {
            return;
        }

        // 先把消息取个哈希
        ArrayList<MessageSeg<?>> content = messageEvent.getMessageChain().getContent();
        Integer curMsgHash = content.hashCode();

        /* 判断是否复读达到 3 次 */
        Long groupId = messageEvent.getGroupId();
        Integer groupSameMessageNumber = 0;
        // 取该群上条消息
        Integer groupLastMessageHash = groupLashMessageMap.getOrDefault(groupId, null);
        if (curMsgHash.equals(groupLastMessageHash)) {
            // 如果与该群上一条消息相同，刷新次数
            groupSameMessageNumber = groupSameMessageNumberMap.getOrDefault(groupId, 1);
            groupSameMessageNumber += 1;
            groupSameMessageNumberMap.put(groupId, groupSameMessageNumber);
        }
        // 如果相同次数达到 3 次，打断复读
        if (groupSameMessageNumber.equals(INTERRUPT_THRESHOLD)) {
            BotAction.sendGroupMessage(
                    groupId,
                    getInterruptChain()
            );
            // 将 group same message number 置 1
            groupSameMessageNumberMap.put(groupId, 1);
        }

        // 刷新 group last message
        groupLashMessageMap.put(groupId, curMsgHash);
    }

    /**
     * @return  获取用于打断复读的消息
     *           这里默认使用表情包
     */
    private MessageChain getInterruptChain() {
        MessageChain chain = new MessageChain();
        // TODO 待优化
        chain.add(new ImageSeg(DEFAULT_INTERRUPT_IMAGE_PATH));
        return chain;
    }

    @Override
    public PatternDetector detector() {
        return new PatternDetector(
                chain -> true
        );
    }

    @Override
    public String helpInfo() {
        return """
                检测到群聊内连续三条相同消息时，发送表情包打断复读。
                BOT 自身消息不参与。""";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessageTypeEnum.GROUP;
    }
}
