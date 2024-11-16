package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.XmlData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: XmlSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 2:02
 * @description: XML 消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("xml")
@EqualsAndHashCode(callSuper = true)
public class XmlSeg extends MessageSeg<XmlData> {

}
