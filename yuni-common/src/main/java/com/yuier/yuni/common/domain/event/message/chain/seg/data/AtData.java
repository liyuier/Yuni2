package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true)
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
}
