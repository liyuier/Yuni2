package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetImagePojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/5/1 17:18
 * @description: 获取图片详情 Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetImagePojo {
    /**
     * 收到的图片文件名
     * 消息段的 file 参数
      */
    private String file;
}
