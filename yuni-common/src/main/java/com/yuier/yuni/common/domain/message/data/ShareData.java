package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: ShareData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:09
 * @description: 链接分享消息段 data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.SHARE)
public class ShareData extends MessageData {
    private String url;
    private String title;
    // 发送时可选，内容描述
    private String content;
    // 发送时可选，图片 URL
    private String image;

    @Override
    public String toString() {
        return "[链接分享<url=" + this.url + "><title=" + this.title + ">]";
    }
}
