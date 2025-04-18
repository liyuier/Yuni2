package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MusicData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: MusicSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:48
 * @description: 音乐分享消息段
 */

@Data
@JsonTypeDefine("music")
@EqualsAndHashCode(callSuper = true)
public class MusicSeg extends MessageSeg<MusicData> {

    public MusicSeg() {
        this.data = new MusicData();
    }
}
