package com.yuier.yuni.common.domain.onebotapi.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: GetMsgResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/22 23:08
 * @description: 获取消息相应类 data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetMessageResData implements OneBotApiData {
    private Long time;
    private String messageType;
    private Long messageId;
    private Long realId;
    private MessageSender sender;
    private ArrayList<MessageSeg<?>> message;

    // llOneBot 自行添加字段
    // 机器人账号
    private Long selfId;
    // 发送消息的 QQ 用户账号
    private Long userId;
    private String rawMessage;
    // 字体
    private Integer font;
    private String subType;
    // 消息形式（CQ 码还是消息段）
    private String messageFormat;
    // 不懂这个字段啥意思
    private String postType;
    private Long groupId;
}
