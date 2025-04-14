package com.yuier.yuni.common.domain.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: OneBotEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event
 * @Date 2024/11/10 20:35
 * @description: OneBot 上报事件总父类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,  // 使用子类名称来自动适配子类
              property = "postType",  // 指定配置子类型的字段为 postType
              defaultImpl = OneBotEvent.class,  // 未设置 postType 时默认的解析类型，这里设为 OneBotEvent 本身
              visible = true)  // 反序列化时 property 配置的字段是否解析出值放在结果中
public class OneBotEvent {

    // 事件发生的时间戳
    private Long time;

    // 收到消息的机器人 QQ 号
    private Long selfId;

    /**
     * 事件类型。此处用于标注子类
     * message：消息事件
     * notice：通知事件
     * request：请求事件
     * meta_event：元事件
     */
    private String postType;

    private OneBotEventPosition position;
}
