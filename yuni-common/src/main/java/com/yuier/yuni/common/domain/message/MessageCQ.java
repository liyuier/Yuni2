package com.yuier.yuni.common.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: MessageCQ
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.message
 * @Date 2024/4/10 21:21
 * @description: CQ 码格式的消息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCQ {

    // CQ 码
    private String CQCode;
}
