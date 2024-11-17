package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupInfoPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/4/23 23:29
 * @description: 获取群信息 Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupInfoPojo {
    private Long groupId;
    /**
     * 是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     * 默认 false
     */
    private Boolean noCache;

    public GetGroupInfoPojo(Long groupId) {
        this.groupId = groupId;
        this.noCache = false;
    }
}
