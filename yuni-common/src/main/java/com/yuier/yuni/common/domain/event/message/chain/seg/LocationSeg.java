package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.LocationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: LocationSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:44
 * @description: 位置消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("location")
@EqualsAndHashCode(callSuper = true)
public class LocationSeg extends MessageSeg {
    LocationData data;
}
