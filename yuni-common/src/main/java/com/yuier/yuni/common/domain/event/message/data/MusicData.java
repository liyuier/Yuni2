package com.yuier.yuni.common.domain.event.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: MusicData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:14
 * @description: 音乐分享消息段（发） data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.MUSIC)
public class MusicData extends MessageData {
    /**
     * 可能的值：qq 163 xm
     * 分别表示使用 QQ 音乐、网易云音乐、虾米音乐
     */
    private String type;
    // 歌曲 ID
    private String id;

    // 以下为歌曲自定义分享类字段

    // private String type;  // 值为 custom 表示音乐自定义分享

    // 点击后跳转目标 URL
    private String url;
    // 音乐 URL
    private String audio;
    // 标题
    private String title;
    // 发送时可选，内容描述
    private String content;
    // 发送时可选，图片 URL
    private String image;

    @Override
    public String toString() {
        return "[分享音乐" + (!this.type.equals("custom")
                ? "<平台=" + this.type + "><歌曲ID=" + this.id
                : "<url=" + this.url + "><歌曲url=" + this.audio + "><标题=" + this.title + ">]");
    }
}
