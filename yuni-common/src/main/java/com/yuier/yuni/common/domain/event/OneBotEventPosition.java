package com.yuier.yuni.common.domain.event;

import com.yuier.yuni.common.enums.OneBotEventPositionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: OneBotEventPosition
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event
 * @Date 2025/4/14 23:49
 * @description: OneBot 事件发生位置
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneBotEventPosition {

    // 事件来源类型
    private OneBotEventPositionEnum eventPosition;

    /**
     * 事件来源位置
     * 如果事件来自群聊，则为群号；
     * 如果事件来自私聊，则为 QQ 号
     */
    private Long positionId;

    public String getPositionStr() {
        return eventPosition.toString();
    }
}
