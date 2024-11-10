package com.yuier.yuni.common.domain.event.message.chain.seg;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.ContactData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ContactSeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain.seg
 * @Date 2024/11/11 1:29
 * @description: 推荐好友/群消息段
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeDefine("contact")
@EqualsAndHashCode(callSuper = true)
public class ContactSeg extends ReMessageSeg {
    ContactData data;
}
