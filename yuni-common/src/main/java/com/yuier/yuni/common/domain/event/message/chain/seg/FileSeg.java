package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: FileSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:40
 * @description: 文件消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("file")
@EqualsAndHashCode(callSuper = true)
public class FileSeg extends MessageSeg {
    FileData data;
}
