package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.AnonymousData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: AnonymousSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:20
 * @description: 匿名消息段
 */

@Data
@JsonTypeDefine("anonymous")
@EqualsAndHashCode(callSuper = true)
public class AnonymousSeg extends MessageSeg<AnonymousData> {
    public AnonymousSeg() {
        this.data = new AnonymousData();
    }
}
