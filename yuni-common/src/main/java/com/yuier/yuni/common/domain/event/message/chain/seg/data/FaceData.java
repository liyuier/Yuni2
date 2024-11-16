package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: FaceData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:38
 * @description: QQ 表情消息 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MessageDataEntity(dataType = MessageDataEnum.FACE)
public class FaceData extends MessageData {
    private String id;

    @Override
    public String toString() {
        return "[QQ表情#" + this.id + "]";
    }
}
