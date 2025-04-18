package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Title: VideoData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:46
 * @description: 短视频消息段 data 类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.VIDEO)
public class VideoData extends MessageData {
    // 文件名
    private String file;
    // 图片 URL
    private String url;
    // 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
    private String cache;
    // 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
    private String proxy;
    // 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时事件，默认不超时
    private String timeout;

    @Override
    public String toString() {
        return "[视频" + "<file=" + this.file + "><url=" + this.url + ">]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoData videoData = (VideoData) o;
        return Objects.equals(file, videoData.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
