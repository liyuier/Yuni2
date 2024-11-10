package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: DiceData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:01
 * @description: 掷骰子消息段 data 类
 */
@Data
@NoArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.DICE)
public class DiceData {
    @Override
    public String toString() {
        return "[投骰子]";
    }
}
