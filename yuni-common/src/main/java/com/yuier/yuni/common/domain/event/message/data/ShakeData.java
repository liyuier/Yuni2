package com.yuier.yuni.common.domain.event.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: ShakeData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:02
 * @description: 窗口抖动消息段（发） data 类
 */
@Data
@Component
@NoArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.SHAKE)
public class ShakeData extends MessageData {

    @Override
    public String toString() {
        return "[窗口抖动消息]";
    }
}
