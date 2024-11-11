package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.JsonData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: JsonSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:43
 * @description: JSON 消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("json")
@EqualsAndHashCode(callSuper = true)
public class JsonSeg extends MessageSeg {
    JsonData data;
}
