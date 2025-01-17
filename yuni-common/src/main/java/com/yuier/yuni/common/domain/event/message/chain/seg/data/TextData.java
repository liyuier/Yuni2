package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: TextData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:36
 * @description: 纯文本消息 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MessageDataEntity(dataType = MessageDataEnum.TEXT)
public class TextData extends MessageData {
    private String text;

    @Override
    public String toString() {
        return this.text;
    }
}
