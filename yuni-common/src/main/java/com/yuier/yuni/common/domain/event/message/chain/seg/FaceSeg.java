package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.FaceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: FaceSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:36
 * @description: 表情消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("face")
@EqualsAndHashCode(callSuper = true)
public class FaceSeg extends MessageSeg {
    FaceData data;
}
