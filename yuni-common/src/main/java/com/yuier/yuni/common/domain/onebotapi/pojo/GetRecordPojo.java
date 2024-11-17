package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetRecordPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/5/1 17:24
 * @description: 获取语音 Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetRecordPojo {
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
