package com.yuier.yuni.common.domain.onebotapi.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SendMessageResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/22 22:32
 * @description: OneBot 发送消息接口响应 data 字段实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendMessageResData implements OneBotApiData {
    private Long messageId;

    public SendMessageResData(SendGroupMessageResData data) {
        this.messageId = data.getMessageId();
    }

    public SendMessageResData(SendPrivateMessageResData data) {
        this.messageId = data.getMessageId();
    }
}
