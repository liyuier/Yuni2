package com.yuier.yuni.common.domain.message.nodemessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: NodeMessageSeg
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.message
 * @Date 2024/4/10 22:58
 * @description: 合并转发自定义结点消息段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeMessageSeg {

    private String type;

    private NodeMessageData data;
}
