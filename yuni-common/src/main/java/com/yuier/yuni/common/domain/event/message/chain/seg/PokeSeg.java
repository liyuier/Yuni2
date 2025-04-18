package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.PokeData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: PokeSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:50
 * @description: 戳一戳消息段
 */

@Data
@JsonTypeDefine("poke")
@EqualsAndHashCode(callSuper = true)
public class PokeSeg extends MessageSeg<PokeData> {

    public PokeSeg() {
        this.data = new PokeData();
    }
}
