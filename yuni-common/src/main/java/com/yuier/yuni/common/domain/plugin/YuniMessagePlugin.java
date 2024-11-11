package com.yuier.yuni.common.domain.plugin;

import com.yuier.yuni.common.enums.MessageTypeEnum;
import com.yuier.yuni.common.enums.PermissionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Title: YuniMessagePlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.plugin
 * @Date 2024/11/11 23:34
 * @description: 由消息事件触发的插件定义类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YuniMessagePlugin extends YuniNegativePlugin {

    // 可触发该插件的权限等级
    private PermissionLevel permission;

    // 插件监听的消息类型
    private MessageTypeEnum listener;
}
