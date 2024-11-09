package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: MarketFaceData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/20 17:46
 * @description: 商城表情 data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.MARKETFACE)
public class MarketFaceData extends MessageData {
    private String summary;
    private String url;
    private String emojiId;
    private String emojiPackageId;
    private String key;

    @Override
    public String toString() {
        return "[商城表情#" + this.summary + "]";
    }
}
