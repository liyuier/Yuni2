package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: RpsData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:55
 * @description: 猜拳魔法表情
 */
@Data
@NoArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.RPS)
public class RpsData {

    @Override
    public String toString() {
        return "[猜拳魔法表情]";
    }
}
