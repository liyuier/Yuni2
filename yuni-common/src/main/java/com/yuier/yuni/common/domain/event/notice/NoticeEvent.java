package com.yuier.yuni.common.domain.event.notice;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: NoticeEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.notice
 * @Date 2025/4/14 1:11
 * @description: 通知事件基类
 * 参考 <a href="https://283375.github.io/onebot_v11_vitepress/event/notice.html">...</a>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeDefine("notice")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "notice_type",
        defaultImpl = NoticeEvent.class,
        visible = true)
public class NoticeEvent extends OneBotEvent {

    /**
     * 通知类型
     *   - 群文件上传  group_upload
     *   - 群管理员变动  group_admin
     *   - 群成员减少  group_decrease
     *   - 群成员增加  group_increase
     *   - 群禁言  group_ban
     *   - 好友添加  friend_add
     *   - 群消息撤回  group_recall
     *   - 好友消息撤回  friend_recall
     *   - 群内戳一戳  notify
     *   - 群红包运气王  lucky_king
     *   - 群成员荣誉变更  honor
     */
    String noticeType;

    /**
     * 群 ID ，并不总有
     */
    Long groupId;

    /**
     * 用户 ID ，并不总有
     */
    Long userId;

    NoticeEventPosition position;
}
