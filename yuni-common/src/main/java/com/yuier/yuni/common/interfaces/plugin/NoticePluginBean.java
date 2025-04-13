package com.yuier.yuni.common.interfaces.plugin;

import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.interfaces.detector.notice.NoticeDetector;

/**
 * @Title: NoticePluginBean
 * @Author yuier
 * @Package com.yuier.yuni.common.interfaces.plugin
 * @Date 2025/4/14 1:24
 * @description: 通知事件插件
 */

public interface NoticePluginBean<T extends NoticeEvent> extends NegativePluginBean<T, NoticeDetector> {

}
