package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.NodeData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: NodeSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:49
 * @description: 合并转发消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("node")
@EqualsAndHashCode(callSuper = true)
public class NodeSeg extends MessageSeg<NodeData> {

}
