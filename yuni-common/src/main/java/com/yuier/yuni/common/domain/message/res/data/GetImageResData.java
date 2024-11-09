package com.yuier.yuni.common.domain.message.res.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetImageResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/5/1 17:20
 * @description: 获取图片响应类 data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetImageResData {
    /**
     * 下载后的图片文件路径
     * 如：/home/somebody/cqhttp/data/image/6B4DE3DFD1BD271E3297859D41C530F5.jpg
     */
    private String file;
    private String fileName;
    private String fileSize;
    /**
     * 文件的 base64 编码。需要在配置文件中开启。
     */
    private String base64;
}
