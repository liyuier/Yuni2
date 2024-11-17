package com.yuier.yuni.common.domain.onebotapi.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SendPrivateMessageResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.onebotapi.data
 * @Date 2024/11/17 19:05
 * @description: 发送私聊消息的响应体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendPrivateMessageResData implements OneBotApiData {
    private Long messageId;
}
