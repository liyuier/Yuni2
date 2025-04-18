package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: TextSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:59
 * @description: 纯文本消息段
 */

@Data
@JsonTypeDefine("text")
@EqualsAndHashCode(callSuper = true)
public class TextSeg extends MessageSeg<TextData> {

    public TextSeg(TextData textData) {
        this.setType("text");
        this.data = textData;
    }

    public TextSeg(String text) {
        this(new TextData(text));
    }

    public TextSeg() {
        this.data = new TextData();
    }
}
