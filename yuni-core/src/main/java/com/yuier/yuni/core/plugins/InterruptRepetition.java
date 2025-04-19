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

import java.security.SecureRandom;
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

    // 群聊中上一条消息哈希
    HashMap<Long, Integer> groupLashMessageMap = new HashMap<>();
    // 群聊中相同消息出现次数
    HashMap<Long, Integer> groupSameMessageNumberMap = new HashMap<>();
    // 群聊中本轮复读次数
    HashMap<Long, Integer> groupMaxRepeatTimeMap = new HashMap<>();

    SecureRandom secureRandom = new SecureRandom();

    static final String[] INTERRUPT_IMAGES_PATHS = {
            "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition_zago.jpeg",
            "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition_goodfriend.jpeg",
            "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition_fk.jpeg",
            "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition_soyorin.jpeg"
    };

    static final String INTERRUPT_FIVE_REPEATS_IMAGE_PATH = "https://yui-bucket-1309363843.cos.ap-nanjing.myqcloud.com/interrupt_repetition_gameking.png";

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
        // 如果相同次数达到本轮打断阈值，打断复读
        Integer groupMaxRepeatTime = getGroupMaxRepeatTime(groupId);
        if (groupSameMessageNumber.equals(groupMaxRepeatTime)) {
            BotAction.sendGroupMessage(
                    groupId,
                    getInterruptChain(groupMaxRepeatTime)
            );
            // 刷新
            groupSameMessageNumberMap.put(groupId, 1);
            groupLashMessageMap.put(groupId, 0);
            groupMaxRepeatTimeMap.put(groupId, getRandomRepeatTime());
            return;
        }

        // 刷新 group last message
        groupLashMessageMap.put(groupId, curMsgHash);
    }

    /**
     * 获取当前群聊允许的最大复读次数
     * @param groupId  群号
     * @return  当前群聊允许的最大复读次数
     */
    private Integer getGroupMaxRepeatTime(Long groupId) {
        Integer time = groupMaxRepeatTimeMap.getOrDefault(groupId, 0);
        if (time.equals(0)) {
            time = getRandomRepeatTime();
            groupMaxRepeatTimeMap.put(groupId, time);
        }
        return time;
    }

    /**
     * @return  从 3, 4, 5 中随机挑选一个数字
     */
    private Integer getRandomRepeatTime() {
        int time = 0;
        // 随机赋值
        int [] numbers = {3, 4, 5};
        time = numbers[secureRandom.nextInt(numbers.length)];
        return time;
    }

    /**
     * @return  获取用于打断复读的消息
     *           这里默认使用表情包
     */
    private MessageChain getInterruptChain(Integer groupMaxRepeatTime) {
        MessageChain chain = new MessageChain();
        if (groupMaxRepeatTime == 5) {
            chain.add(new ImageSeg(INTERRUPT_FIVE_REPEATS_IMAGE_PATH));
            return chain;
        }
        int imageIndex = secureRandom.nextInt(INTERRUPT_IMAGES_PATHS.length);
        chain.add(new ImageSeg(INTERRUPT_IMAGES_PATHS[imageIndex]));
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
                当群内出现复读现象时，正义的 bot 将会从天而降将其打断！
                打断前的最大复读次数在 3, 4, 5 次中随机选择；将从打断表情包池中随机挑选一张打断。
                如果本次复读次数为 5 次，那么必定触发 `群陨石` 表情包。""";
    }

    @Override
    public MessageTypeEnum listenAt() {
        return MessageTypeEnum.GROUP;
    }
}
