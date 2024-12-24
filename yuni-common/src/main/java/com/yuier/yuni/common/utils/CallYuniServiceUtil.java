package com.yuier.yuni.common.utils;

/**
 * @Title: CallYuniServiceUtil
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/12/23 22:49
 * @description: 微服务调用工具类
 */

public class CallYuniServiceUtil {

    private String yuniServiceBaseUrl;

    public CallYuniServiceUtil(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        yuniServiceBaseUrl = baseUrl;
    }

    public <T> T getOneBotForEntity(String api, Class<T> responseDataType) {
        return YuniBaseHttpUtil.getForEntity(yuniServiceBaseUrl + api, responseDataType);
    }

    public <T, S> T postOneBotForEntity(String api, S requestBody, Class<S> requestBodyClass, Class<T> responseDataType) {
        return YuniBaseHttpUtil.postForEntity(yuniServiceBaseUrl + api, requestBody, requestBodyClass, responseDataType);
    }
}
