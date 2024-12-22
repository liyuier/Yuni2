package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;

/**
 * @Title: CallOneBotUtil
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/12/23 0:06
 * @description: 调用 OneBot API 工具类
 */
public class CallOneBotUtil {

    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType) {
        return YuniBaseHttpUtil.getForEntity(url, responseDataType);
    }

    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<S> requestBodyClass, Class<T> responseDataType) {
        return YuniBaseHttpUtil.postForEntity(url, requestBody, requestBodyClass, responseDataType);
    }
}
