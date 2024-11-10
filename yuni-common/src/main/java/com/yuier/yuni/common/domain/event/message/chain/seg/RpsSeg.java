package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.RpsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: RpsSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:54
 * @description: 猜拳魔法表情
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("rps")
@EqualsAndHashCode(callSuper = true)
public class RpsSeg extends ReMessageSeg {
    RpsData data;
}
