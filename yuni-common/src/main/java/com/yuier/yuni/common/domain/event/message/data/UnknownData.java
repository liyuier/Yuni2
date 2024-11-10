package com.yuier.yuni.common.domain.event.message.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Title: UnknownData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 23:11
 * @description: 无法识别消息段类型
 */
@Data
@Component
@AllArgsConstructor
public class UnknownData extends MessageData {
}
