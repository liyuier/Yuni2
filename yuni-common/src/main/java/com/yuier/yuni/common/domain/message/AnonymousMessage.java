package com.yuier.yuni.common.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: AnonymousMessage
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.message
 * @Date 2024/4/10 23:41
 * @description: 群聊消息中 anonymous 字段类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousMessage {

    // 匿名用户 ID
    private Long id;

    // 匿名用户名称
    private String name;

    // 匿名用户 flag，在调用禁言 API 时需要传入
    private String flag;
}
