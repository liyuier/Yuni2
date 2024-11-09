package com.yuier.yuni.common.enums;

import lombok.Getter;

/**
 * @Title: BiliAuthenticateMethodEnum
 * @Author yuier
 * @Package com.yuier.yuni.common.enums
 * @Date 2024/5/20 19:47
 * @description: B站 API 鉴权方式
 */
@Getter
public enum BiliAuthenticateMethodEnum {
    COOKIE("cookie", "cookie 鉴权"),
    APP_SIGN("app_sign", "app 签名鉴权"),
    WBI_SIGN("wbi_sign", "wbi 签名鉴权");



    private final String method;
    private final String description;

    BiliAuthenticateMethodEnum(String method, String description) {
        this.method = method;
        this.description = description;
    }

    @Override
    public String toString() {
        return method;
    }
}
