package com.yuier.yuni.common.domain.message.nodemessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: MessageSegVo
 * @Author yuier
 * @Package com.yuier.yuni.core.domain.message
 * @Date 2024/4/10 23:07
 * @description: 转发消息中 data.content 字段中的 MessageSeg 类，以适配嵌套转发消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeMessageContentSeg<T> {
    // 消息段类型
    private String type;

    // 消息段数据
    private ArrayList<T> data;
}
