package com.yuier.yuni.common.domain.event.notice;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.constants.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: GroupRecallNoticeEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.notice
 * @Date 2025/4/14 1:18
 * @description: 群消息撤回事件
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeDefine(SystemConstants.NOTICE_TYPE.GROUP_RECALL)
public class GroupRecallNoticeEvent extends NoticeEvent{

    /**
     * 群号
     */
    Long groupId;

    /**
     * 消息发送者 QQ 号
     */
    Long userId;

    /**
     * 操作者 QQ 号
     */
    Long operatorId;

    /**
     * 被撤回的消息 ID
     */
    Long messageId;

    @Override
    public String toString() {
        return "收到群消息撤回事件。群号: <" + groupId + "> ; 消息 ID : <" + messageId + ">";
    }
}
