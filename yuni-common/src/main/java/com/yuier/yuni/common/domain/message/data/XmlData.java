package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: XmlData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:35
 * @description: xml 消息段 data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.XML)
public class XmlData extends MessageData {
    // XML 内容
    private String data;

    @Override
    public String toString() {
        return "[XML消息<" + this.data + ">]";
    }
}
