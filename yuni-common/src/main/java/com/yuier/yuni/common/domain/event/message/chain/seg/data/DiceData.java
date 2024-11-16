package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@MessageDataEntity(dataType = MessageDataEnum.DICE)
public class DiceData extends MessageData {
    @Override
    public String toString() {
        return "[投骰子]";
    }
}
