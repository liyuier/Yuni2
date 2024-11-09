package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: AnonymousData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:03
 * @description: 匿名消息段（发）data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.ANONYMOUS)
public class AnonymousData extends MessageData {
    // 可选，表示无法匿名时是否继续发送
    private String ignore;

    @Override
    public String toString() {
        return "[匿名消息]";
    }
}
