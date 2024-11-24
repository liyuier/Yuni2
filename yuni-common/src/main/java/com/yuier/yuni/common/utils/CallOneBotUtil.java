package com.yuier.yuni.common.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.domain.onebotapi.OneBotApiRes;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

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

    private static RestTemplate restTemplate;

    private static ObjectMapper getObjectMapper() {
        return ApplicationContextProvider.getBean(ObjectMapper.class);
    }

    private static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
            return restTemplate;
        }
        return restTemplate;
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param responseDataType  想要接收的响应 data 的 class
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType) {
        // 获取请求头，设置响应内容类型为 JSON
        HttpHeaders header = createJsonContentHeader();
        ResponseEntity<String> responseEntity = null;
        try {
            // 发出请求，这里接收响应的 JSON 消息
            responseEntity = getRestTemplate().getForEntity(new URL(url).toURI(), String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // 获取响应字符串，并解析为返回的实体类
                return parseJsonResult(responseEntity, responseDataType);
            } else {
                throw new RuntimeException("Failed to fetch data from " + url + ". Status code: " + responseEntity.getStatusCode());
            }
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param responseDataType  想要接收的响应 data 的 class
     * @param uriVariables  请求参数
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType, Object... uriVariables) {
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class, uriVariables);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to fetch data from " + url + ". Status code: " + responseEntity.getStatusCode());
        }
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param responseDataType  想要接收的响应 data 的 class
     * @param uriVariables  请求参数
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType, Map<String, ?> uriVariables) {
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class, uriVariables);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to fetch data from " + url + ". Status code: " + responseEntity.getStatusCode());
        }
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param requestBody  请求数据实体类
     * @param responseDataType  想要接收的响应 data 的 class
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType) {
        // 组装请求头，设置响应类型为 JSON 类型
        ResponseEntity<String> responseEntity  = null;
        try {
            responseEntity = getRestTemplate().postForEntity(new URL(url).toURI(), createHeadForJsonContentType(requestBody), String.class);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to post data to " + url + ". Status code: " + responseEntity.getStatusCode());
        }
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param requestBody  请求数据实体类
     * @param responseDataType  想要接收的响应 data 的 class
     * @param uriVariables  请求参数
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType, Object... uriVariables) {
        ResponseEntity<String> responseEntity  = null;
        responseEntity = getRestTemplate().postForEntity(url, createHeadForJsonContentType(requestBody), String.class, uriVariables);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to post data to " + url + ". Status code: " + responseEntity.getStatusCode());
        }
    }

    /**
     * 请求 OneBot API
     * @param url  url
     * @param requestBody  请求数据实体类
     * @param responseDataType  想要接收的响应 data 的 class
     * @param uriVariables  请求参数
     * @return  封装好的 OneBotApiRes 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType, Map<String, ?> uriVariables) {
        ResponseEntity<String> responseEntity  = null;
        responseEntity = getRestTemplate().postForEntity(url, createHeadForJsonContentType(requestBody), String.class, uriVariables);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to post data to " + url + ". Status code: " + responseEntity.getStatusCode());
        }
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

    /**
     * 将 JSON 字符串格式的响应结果解析为 OneBotApiRes<T> 类型
     * @param responseEntity  响应体
     * @param responseDataType  需要获取的 OneBot API 响应结果的 data 字段类型
     * @return  OneBotApiRes<T> 类型的响应结构
     * @param <T>  限定返回值为 OneBotApiData 类型
     */
    public static <T extends OneBotApiData> T parseJsonResult(ResponseEntity<String> responseEntity, Class<T> responseDataType) {
        // 获取响应体中的 json 字符串
        String result = responseEntity.getBody();
        // 获取 objectMapper 实例，该实例中维护了子类关系
        ObjectMapper objectMapper = getObjectMapper();
        // 遇到匹配不上实体类中字段的 json 属性时，跳过该属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 先借助 hutool 的 JSONUtil 获取 OneBotApiRes<?> 实例
        OneBotApiRes<T> oneBotApiRes = JSONUtil.toBean(result, OneBotApiRes.class);
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

    /**
     * 获取请求头
     * 设置响应消息类型为 JSON
     * @return HttpHeaders 实例
     */
    private static HttpHeaders createJsonContentHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 构造 POST 请求体，其中的请求头设置响应消息类型为 JSON
     * @param requestBody  请求数据实体类
     * @return  HttpEntity 实体类
     * @param <T>  反正就是一个泛型吧
     */
    private static <T> HttpEntity<?> createHeadForJsonContentType(T requestBody) {
        return new HttpEntity<>(requestBody, createJsonContentHeader());
    }
}
