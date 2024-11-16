package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MusicData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: MusicSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:48
 * @description: 音乐分享消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("music")
@EqualsAndHashCode(callSuper = true)
public class MusicSeg extends MessageSeg<MusicData> {

}
