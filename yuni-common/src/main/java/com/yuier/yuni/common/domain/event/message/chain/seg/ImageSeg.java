package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ImageData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ImageSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:43
 * @description: 图片消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("image")
@EqualsAndHashCode(callSuper = true)
public class ImageSeg extends MessageSeg<ImageData> {

}
