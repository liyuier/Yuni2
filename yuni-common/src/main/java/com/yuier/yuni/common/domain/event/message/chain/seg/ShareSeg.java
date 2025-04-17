package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ShareData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ShareSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:56
 * @description: 链接分享消息段 data 类
 */

@Data
@NoArgsConstructor
@JsonTypeDefine("share")
@EqualsAndHashCode(callSuper = true)
public class ShareSeg extends MessageSeg<ShareData> {

}
