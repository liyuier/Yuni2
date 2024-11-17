package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetFilePojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/5/1 17:33
 * @description: 下载收到的文件 Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetFilePojo {
    private String fileId;
}
