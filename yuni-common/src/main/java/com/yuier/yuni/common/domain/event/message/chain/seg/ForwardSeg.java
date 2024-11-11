package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ForwardData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ForwardSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:42
 * @description: 转发消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("forward")
@EqualsAndHashCode(callSuper = true)
public class ForwardSeg extends MessageSeg {
    ForwardData data;
}
