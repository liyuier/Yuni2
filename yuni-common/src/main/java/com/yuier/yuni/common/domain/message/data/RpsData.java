package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: RpsData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:55
 * @description: 猜拳魔法表情
 */
@Data
@Component
@NoArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.RPS)
public class RpsData extends MessageData {

    @Override
    public String toString() {
        return "[猜拳魔法表情]";
    }
}
