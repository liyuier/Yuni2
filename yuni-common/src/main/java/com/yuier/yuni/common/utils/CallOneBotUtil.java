package com.yuier.yuni.common.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.domain.onebotapi.OneBotApiRes;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.yuier.yuni.common.constants.OneBotApiRetCode.ASYNC;
import static com.yuier.yuni.common.constants.OneBotApiRetCode.OK;

/**
 * @Title: CallOneBotUtil
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/13 1:08
 * @description: Http 工具类
 * 反正就是一堆工具吧，没什么好写注释的
 */

public class CallOneBotUtil {

    private static WebClient webClient;

    private static ObjectMapper getObjectMapper() {
        return ApplicationContextProvider.getBean(ObjectMapper.class);
    }

    private static WebClient getWebClient() {
        if (webClient == null) {
            webClient = WebClient.builder().build();
        }
        return webClient;
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param responseDataType  想要接收的响应 data 的 class
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType) {
        // 使用 webClient 进行请求
        Mono<String> stringMono = getWebClient().get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        return parseJsonResult(stringMono.block(), responseDataType);
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param requestBody  请求数据实体类
     * @param requestBodyClass  发送的请求体的 class
     * @param responseDataType  想要接收的响应 data 的 class
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<S> requestBodyClass, Class<T> responseDataType) {
        Mono<String> stringMono = getWebClient().post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestBodyClass)
                .retrieve()
                .bodyToMono(String.class);
        return parseJsonResult(stringMono.block(), responseDataType);
    }

    /**
     * 处理 OneBot 响应
     * @param response  OneBot 响应体
     * @return  OneBotApiRes 中的 data 字段
     * @param <T>  限制入参实现了 OneBotApiData 接口
     */
    private static <T extends OneBotApiData> T checkOneBotResponse(OneBotApiRes<T> response) {
        switch (response.getRetcode()) {
            case OK:
                break;
            case ASYNC:
                throw new RuntimeException("已提交异步处理。");
            default:
                throw new RuntimeException("OneBot API 调用失败，响应信息: " + response);
        }
        return response.getData();
    }

    public static <T extends OneBotApiData> T parseJsonResult(String responseJson, Class<T> responseDataType) {
        // 获取 objectMapper 实例，该实例中维护了子类关系
        ObjectMapper objectMapper = getObjectMapper();
        // 遇到匹配不上实体类中字段的 json 属性时，跳过该属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 先借助 hutool 的 JSONUtil 获取 OneBotApiRes<?> 实例
        OneBotApiRes<T> oneBotApiRes = JSONUtil.toBean(responseJson, OneBotApiRes.class);
        // 然后再将半成品的 oneBotApiRes.data 作为字符串再取出（TODO 我的天，这里一定要优化）
        String resDataJson = JSONUtil.toJsonStr((JSONObject) oneBotApiRes.getData());
        T resData;
        try {
            // 再借助 objectMapper 解析 data
            resData = objectMapper.readValue(resDataJson, responseDataType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 完成
        return checkOneBotResponse(oneBotApiRes.build(resData));
    }
}
