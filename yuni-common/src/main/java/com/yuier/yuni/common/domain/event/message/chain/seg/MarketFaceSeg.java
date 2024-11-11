package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MarketFaceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: MarketFaceSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:47
 * @description: 商城表情消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("mface")
@EqualsAndHashCode(callSuper = true)
public class MarketFaceSeg extends MessageSeg {
    MarketFaceData data;
}
