package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: AppHttpCodeEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/4/9 23:36
 * @description: 响应码枚举类
 */
@Getter
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    SYSTEM_ERROR(500,"出现错误");
    final int code;
    final String msg;

    // 构造方法
    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

}
