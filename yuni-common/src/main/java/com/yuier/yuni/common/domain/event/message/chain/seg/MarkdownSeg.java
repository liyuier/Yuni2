package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MarkdownData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: MarkdownSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:45
 * @description: markdown 消息段
 */

@Data
@JsonTypeDefine("markdown")
@EqualsAndHashCode(callSuper = true)
public class MarkdownSeg extends MessageSeg<MarkdownData> {

    public MarkdownSeg() {
        this.data = new MarkdownData();
    }
}
