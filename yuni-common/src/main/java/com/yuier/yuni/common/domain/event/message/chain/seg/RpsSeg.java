package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.RpsData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: RpsSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:54
 * @description: 猜拳魔法表情
 */

@Data
@JsonTypeDefine("rps")
@EqualsAndHashCode(callSuper = true)
public class RpsSeg extends MessageSeg<RpsData> {

    public RpsSeg() {
        this.data = new RpsData();
    }
}
