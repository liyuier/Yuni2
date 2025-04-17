package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ShakeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ShakeSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:55
 * @description: 窗口抖动消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("shake")
@EqualsAndHashCode(callSuper = true)
public class ShakeSeg extends MessageSeg<ShakeData> {

}
