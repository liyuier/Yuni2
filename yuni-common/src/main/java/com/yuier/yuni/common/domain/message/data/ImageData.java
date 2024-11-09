package com.yuier.yuni.common.domain.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: ImageData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 21:42
 * @description: 图片消息段 data 类
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.IMAGE)
public class ImageData extends MessageData {
    /**
     * 文件名
     * 发送时，除了支持使用收到的图片文件名直接发送外，还支持：
     * 1. 绝对路径 file:///path\\to\\img.png
     * 2. 网络 URL http://url/to/img.png
     * 3. Base64 编码 base64://code
     */
    private String file;
    // 图片类型，flash 表示闪照；无此参数表示普通图片
    private String type;
    // 图片 URL
    private String url;
    // 只在通过网络 URL 发送时有效，表示是否使用已缓存图片
    private String cache;
    // 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理）
    private String proxy;
    // 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时事件，默认不超时
    private String timeout;

    // 以下为 LLOneBot 自行添加字段
    private String fileSize;

    /**
     * 发送时自定义图片预览文字
     */
    private String summary;

    public ImageData(String image) {
        this.file = image;
    }

    @Override
    public String toString() {
        return "[图片" + ((null == this.type) ? "" : "<闪照>") + "<file=" + this.file + ">" + ((null == this.url) ? "" : "<url=" + this.url  + ">") +"]";
    }

}
