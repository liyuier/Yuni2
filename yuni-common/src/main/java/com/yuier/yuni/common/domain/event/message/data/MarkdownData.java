package com.yuier.yuni.common.domain.event.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: MarkdownData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/20 17:50
 * @description: markdown 消息 data 字段
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.MARKDOWN)
public class MarkdownData extends MessageData {
    private String data;

    @Override
    public String toString() {
        return "[markdown消息<#" + this.data + ">]";
    }
}
