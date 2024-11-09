package com.yuier.yuni.common.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetRecordDto
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.dto
 * @Date 2024/5/1 17:24
 * @description: 获取语音 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetRecordDto {
    /**
     * 收到的语音文件名。消息段的 file 参数
     * 如：0B38145AA44505000B38145AA4450500.silk
     */
    private String file;
    /**
     * 要转换到的格式
     * 目前支持 mp3、amr、wma、m4a、spx、ogg、wav、flac
     */
    private String outFormat;
}
