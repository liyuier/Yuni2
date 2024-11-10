package com.yuier.yuni.common.domain.event.message;

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
 * @Title: MessageEvent
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event
 * @Date 2024/11/10 21:07
 * @description: 继承 OneBotEvent, 用于接收消息事件的上报
 * 结构参考 https://283375.github.io/onebot_v11_vitepress/event/message.html#%E7%A7%81%E8%81%8A%E6%B6%88%E6%81%AF
 * 字段从父类中的 message_type 开始定义
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeDefine("message")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,  // 使用子类名称来自动适配子类
        property = "message_type",  // 指定配置子类型的字段为 messageType
        defaultImpl = ReMessageEvent.class,  // 未设置 messageType 时默认的解析类型，这里设为 OneBotEvent 本身
        visible = true)  // 反序列化时 property 配置的字段是否解析出值放在结果中
public class ReMessageEvent extends OneBotEvent {
    /**
     * 消息类型。
     * - private 私聊消息
     * - group 群聊消息
     */
    private String messageType;

    /**
     * 消息子类型
     * 私聊消息
     *   - friend 好友会话
     *   - group 群临时会话
     *   - other 其他
     * 群聊消息
     *   - normal 正常消息
     *   - anonymous 匿名消息
     *   - notice 系统提示
     */
    private String subType;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 发送者 QQ 号
     */
    private Long userId;

    /**
     * 消息内容
     * TODO 这里的消息链需要再进行处理
     */
//    private ArrayList<ReMessageSeg> message;

    /**
     * 原始消息内容
     */
    private String rawMessage;

    /**
     * 字体
     */
    private Long font;

    // 以下为 LLOneBot 在 OneBot 消息事件标准之外添加的字段
    // 消息类型，是数组还是 CQ 码
    private String messageFormat;

    // 真实 ID 就是最真实的 ID （划掉）
    // 其实这个字段在协议的 get_msg() 接口上会响应出来
    private Long realId;
}
