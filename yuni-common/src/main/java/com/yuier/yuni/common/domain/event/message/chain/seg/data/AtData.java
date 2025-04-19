package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: AtData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:53
 * @description: @ 消息段 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.AT)
public class AtData extends MessageData {

    // 被 @ 的 QQ 号。all 表示全体成员。
    private String qq;

    // 被 @ 的 QQ 用户名
    private String name;

    @Override
    public String toString() {
        return "[@" + this.qq + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtData atData = (AtData) o;
        return Objects.equals(qq, atData.qq) && Objects.equals(name, atData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qq, name);
    }
}
