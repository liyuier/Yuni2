package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: ReplySeg
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:24
 * @description: 回复消息段 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.REPLY)
public class ReplyData extends MessageData {
    // 回复时引用的消息 ID
    private String id;

    @Override
    public String toString() {
        return "[回复<消息id="+ this.id + ">]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyData replyData = (ReplyData) o;
        return Objects.equals(id, replyData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
