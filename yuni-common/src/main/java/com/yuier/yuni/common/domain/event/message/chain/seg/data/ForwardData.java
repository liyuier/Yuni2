package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: ForwardData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:25
 * @description: 合并转发消息段（收）data 类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.FORWARD)
public class ForwardData {
    /**
     * 合并转发 id，需通过 get_forward_msg API 获取具体内容
     */
    private String id;

    @Override
    public String toString() {
        return "[合并转发消息]";
    }
}
