package com.yuier.yuni.common.domain.message.res.data;

import com.yuier.yuni.common.domain.message.MessageSeg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Title: GetMsgResMessage
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/4/22 23:16
 * @description: 获取消息响应 data 字段 message 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMsgResMessage {
    ArrayList<MessageSeg> message;
}
