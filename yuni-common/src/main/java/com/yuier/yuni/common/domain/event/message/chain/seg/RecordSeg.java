package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.RecordData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: RecordSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:53
 * @description: 语音消息段
 */

@Data
@JsonTypeDefine("record")
@EqualsAndHashCode(callSuper = true)
public class RecordSeg extends MessageSeg<RecordData> {

    public RecordSeg() {
        this.data = new RecordData();
    }
}
