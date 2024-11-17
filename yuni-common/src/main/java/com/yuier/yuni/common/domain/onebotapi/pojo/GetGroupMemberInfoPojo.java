package com.yuier.yuni.common.domain.onebotapi.pojo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: GetGroupMemberInfoPojo
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.Pojo
 * @Date 2024/4/24 22:52
 * @description: 获取群成员信息 Pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetGroupMemberInfoPojo {
    private Long groupId;
    private Long userId;
    // 是否不使用缓存，默认 false
    private Boolean noCache;

    public GetGroupMemberInfoPojo(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
        this.noCache = false;
    }
}
