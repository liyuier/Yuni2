package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: ContactData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:11
 * @description: 推荐好友/群消息段 data 数据
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MessageDataEntity(dataType = MessageDataEnum.CONTACT)
public class ContactData extends MessageData {
    // 推荐好友，可选值： qq/group
    private String type;
    // 被推荐人/群的 QQ 号
    private String id;

    @Override
    public String toString() {
        return "[推荐" + (type.equals("qq") ? "QQ 用户" : "QQ 群 ") + "<" + this.id + ">]";
    }
}
