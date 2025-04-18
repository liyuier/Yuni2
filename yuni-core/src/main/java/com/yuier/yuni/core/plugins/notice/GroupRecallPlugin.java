package com.yuier.yuni.core.plugins.notice;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.constants.SystemConstants;
import com.yuier.yuni.common.detect.notice.NoticeDetectorImpl;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.notice.GroupRecallNoticeEvent;
import com.yuier.yuni.common.domain.onebotapi.data.GetMessageResData;
import com.yuier.yuni.common.enums.SubscribeCondition;
import com.yuier.yuni.common.interfaces.detector.notice.NoticeDetector;
import com.yuier.yuni.common.interfaces.plugin.NoticePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import com.yuier.yuni.core.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: GroupRecallPlugin
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins.notice
 * @Date 2025/4/14 1:33
 * @description: 处理群消息撤回事件的插件
 */

@Slf4j
@Component
@Plugin(name = "消息撤回提醒", subscribe = SubscribeCondition.NO)
public class GroupRecallPlugin implements NoticePluginBean<GroupRecallNoticeEvent> {

    @Autowired
    MessageRecordService messageRecordService;

    @Override
    public void run(GroupRecallNoticeEvent event, NoticeDetector detector) {

        // 获取撤回消息的人
        String operator = BotAction.GetGroupMemberName(event.getGroupId(), event.getOperatorId());
        // 获取原消息
        MessageChain chain = getRecalledMessageChain(event.getMessageId());
        // 获取被撤回的消息发送者
        String rawSender = BotAction.GetGroupMemberName(event.getGroupId(), event.getUserId());

        // 拼接消息
        MessageChain sendChain = new MessageChain();
        sendChain.addTextSeg("[" + operator + "] 撤回了一条消息：\n[" + rawSender + "]\n");
        sendChain.add(chain);

        BotAction.sendGroupMessage(event.getGroupId(), sendChain);

    }

    /**
     * 获取被撤回的消息链
     * @param messageId  被撤回的消息 ID
     * @return  被撤回的消息链
     */
    private MessageChain getRecalledMessageChain(Long messageId) {
        // 先从本地数据库查询
        MessageChain localMessageChain = messageRecordService.queryMessageByMessageId(messageId);
        if (!(localMessageChain == null) && !localMessageChain.isEmpty()) {
            log.info("从本地数据库获取到消息。");
            return localMessageChain;
        }
        // 本地数据库没有查到，去 OneBot 客户端查询
        GetMessageResData message = BotAction.getMessage(messageId);
        return new MessageChain(message.getMessage());
    }

    @Override
    public NoticeDetector detector() {
        return new NoticeDetectorImpl(SystemConstants.NOTICE_TYPE.GROUP_RECALL);
    }

    @Override
    public String helpInfo() {
        return """
                接收到消息撤回事件时，在群内提醒并复述消息""";
    }
}
