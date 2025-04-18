package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ImageData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: ImageSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:43
 * @description: 图片消息段
 */

@Data
@JsonTypeDefine("image")
@EqualsAndHashCode(callSuper = true)
public class ImageSeg extends MessageSeg<ImageData> {
    public ImageSeg(ImageData imageData) {
        this.setType(MessageDataEnum.IMAGE.toString());
        this.data = imageData;
    }

    public ImageSeg(String image) {
        this(new ImageData(image));
    }

    public ImageSeg() {
        this.data = new ImageData();
    }
}
