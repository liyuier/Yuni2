package com.yuier.yuni.common.domain.message.res.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetRecordResData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.res.data
 * @Date 2024/5/1 17:28
 * @description: 获取语音响应类 data 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetRecordResData {
    /**
     * 转换后的语音文件路径，
     * 如：/home/somebody/cqhttp/data/record/0B38145AA44505000B38145AA4450500.mp3
     */
    private String file;
    private String fileName;
    private String fileSize;
    /**
     * 文件的 base64 编码。需要在配置文件中开启。
     */
    private String base64;
}
