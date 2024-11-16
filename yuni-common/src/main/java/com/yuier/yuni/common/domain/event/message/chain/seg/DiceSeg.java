package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.DiceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: DiceSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:33
 * @description: 掷骰子消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("dice")
@EqualsAndHashCode(callSuper = true)
public class DiceSeg extends MessageSeg<DiceData> {

}
