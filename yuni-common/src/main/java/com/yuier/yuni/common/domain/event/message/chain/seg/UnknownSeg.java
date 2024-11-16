package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.domain.event.message.chain.seg.data.UnknownData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: UnknownSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 2:00
 * @description: 无法识别消息段类型
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnknownSeg extends MessageSeg<UnknownData> {

}
