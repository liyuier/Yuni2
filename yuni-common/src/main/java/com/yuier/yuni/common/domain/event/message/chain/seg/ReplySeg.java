package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ReplyData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ReplySeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:53
 * @description: 回复消息段
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("reply")
@EqualsAndHashCode(callSuper = true)
public class ReplySeg extends MessageSeg<ReplyData> {

}
