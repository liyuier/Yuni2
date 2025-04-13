package com.yuier.yuni.core.randosoru.plugin;

import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.domain.plugin.YuniNoticePlugin;
import com.yuier.yuni.common.interfaces.detector.notice.NoticeDetector;

/**
 * @Title: NoticeMatcher
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.plugin
 * @Date 2025/4/14 2:31
 * @description: 消息事件匹配器
 */

public class NoticeMatcher {

    /**
     * 检查通知能否匹配通知探测器
     * @param event 通知事件
     * @param plugin 插件
     * @return 消息是否命中插件
     */
    public static Boolean matchNoticeDetector(NoticeEvent event, YuniNoticePlugin plugin) {
        NoticeDetector detector = (NoticeDetector) plugin.getDetector();
        return detector.hit(event);
    }
}
