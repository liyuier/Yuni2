package com.yuier.yuni.common.domain.onebotapi.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: SendGroupMessageResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.onebotapi.data
 * @Date 2024/11/17 18:21
 * @description: 发送群消息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SendGroupMessageResData implements OneBotApiData {
    private Long messageId;
}
