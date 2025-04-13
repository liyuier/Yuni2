package com.yuier.yuni.common.domain.event.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: NoticeEventPosition
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.notice
 * @Date 2025/4/14 2:41
 * @description: 通知事件位置
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEventPosition {

    // 通知来源 ID
    // 群和私聊收到的通知一定不会相同，所以这里只需要一个来源 ID 即可
    private Long positionId;
}
