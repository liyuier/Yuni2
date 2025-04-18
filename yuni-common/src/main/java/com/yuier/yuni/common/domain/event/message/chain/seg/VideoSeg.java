package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.VideoData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: VideoSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 2:01
 * @description: 短视频消息段
 */

@Data
@JsonTypeDefine("video")
@EqualsAndHashCode(callSuper = true)
public class VideoSeg extends MessageSeg<VideoData> {

    public VideoSeg() {
        this.data = new VideoData();
    }
}
