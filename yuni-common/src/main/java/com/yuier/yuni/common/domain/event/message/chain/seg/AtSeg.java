package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.AtData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: AtSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:22
 * @description: @ 消息段
 */

@Data
@JsonTypeDefine("at")
@EqualsAndHashCode(callSuper = true)
public class AtSeg extends MessageSeg<AtData> {
    public AtSeg() {
        this.data = new AtData();
    }
}
