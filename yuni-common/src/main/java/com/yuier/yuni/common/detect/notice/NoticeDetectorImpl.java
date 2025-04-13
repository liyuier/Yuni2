package com.yuier.yuni.common.detect.notice;

import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.interfaces.detector.notice.NoticeDetector;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: NoticeDetectorImpl
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.notice
 * @Date 2025/4/14 1:28
 * @description: 通知事件探测器实现类
 */

@Data
@AllArgsConstructor
public class NoticeDetectorImpl implements NoticeDetector {

    private String noticeType;

    @Override
    public Boolean hit(NoticeEvent event) {
        return event.getNoticeType().equals(noticeType);
    }
}
