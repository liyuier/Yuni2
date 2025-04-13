package com.yuier.yuni.common.anno;

import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.enums.SubscribeCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: Plugin
 * @Author yuier
 * @Package com.yuier.yuni.common.anno
 * @Date 2024/11/8 23:28
 * @description: 插件注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {
    /**
     * @return 插件 ID，可自定义
     *         默认为插件名 —— 由框架实现
     */
    String name() default "";

    /**
     * @return 可触发该插件的权限等级，仅当上报事件为消息事件时使用。
     *         默认权限等级为 user
     */
    PermissionLevel permission() default PermissionLevel.USER;

    /**
     * @return 作为新插件，第一次拉起时，希望机器人实例默认订阅还是不订阅
     *         默认订阅
     */
    SubscribeCondition subscribe() default SubscribeCondition.YES;

    /**
     * @return 是否为内置插件。内置插件无法删除。
     */
    boolean inner() default false;
}
