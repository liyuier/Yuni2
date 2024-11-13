package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: TextSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:59
 * @description: 纯文本消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("text")
@EqualsAndHashCode(callSuper = true)
public class TextSeg extends MessageSeg {
    TextData data;

    public TextSeg(TextData textData) {
        this.setType("text");
        this.data = textData;
    }
}
