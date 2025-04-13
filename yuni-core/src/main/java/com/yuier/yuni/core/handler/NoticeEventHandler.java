package com.yuier.yuni.core.handler;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.domain.event.notice.NoticeEventPosition;
import com.yuier.yuni.common.enums.NoticeEventEnum;
import com.yuier.yuni.common.utils.YuniLogUtils;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: NoticeEventHandler
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2025/4/14 3:21
 * @description: 通知事件处理器
 */

@Slf4j
@Component
@EventHandler(eventType = MessageEvent.class)
public class NoticeEventHandler {

    @Autowired
    PluginManager pluginManager;

    public <T extends NoticeEvent> void handle(T noticeEvent) {
        preHandle(noticeEvent);
        pluginManager.matchAndExecNoticeEvent(noticeEvent);
    }

    private <T extends NoticeEvent> void preHandle(T noticeEvent) {
        // 设置消息位置
        String noticeType = noticeEvent.getNoticeType();
        if (noticeType.equals(NoticeEventEnum.FRIEND_ADD.toString()) ||
                noticeType.equals(NoticeEventEnum.FRIEND_RECALL.toString())) {
            noticeEvent.setPosition(new NoticeEventPosition(noticeEvent.getUserId()));
        } else {
            noticeEvent.setPosition(new NoticeEventPosition(noticeEvent.getGroupId()));
        }
        // 打印事件日志
        log.info(YuniLogUtils.receiveNoticeLogStr(noticeEvent));
    }
}
