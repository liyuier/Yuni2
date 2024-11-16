package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: UnknownData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 23:11
 * @description: 无法识别消息段类型
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnknownData extends MessageData {
}
