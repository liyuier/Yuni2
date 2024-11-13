package com.yuier.yuni.common.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yuier.yuni.common.domain.onebotapi.ApiData;
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

    /**
     * 请求 OneBot API
     * @param url  url
     * @param responseDataType  想要接收的响应 data 的 class
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType) {
        // 获取请求头，设置响应内容类型为 JSON
        HttpHeaders header = createJsonContentHeader();
        ResponseEntity<String> responseEntity = null;
        try {
            // 发出请求，这里接收响应的 JSON 消息
            responseEntity = new RestTemplate().getForEntity(new URL(url).toURI(), String.class);
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
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType, Object... uriVariables) {
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class, uriVariables);
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
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     */
    public static <T extends OneBotApiData> T getOneBotForEntity(String url, Class<T> responseDataType, Map<String, ?> uriVariables) {
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class, uriVariables);
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
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType) {
        // 组装请求头，设置响应类型为 JSON 类型
        ResponseEntity<String> responseEntity  = null;
        try {
            responseEntity = new RestTemplate().postForEntity(new URL(url).toURI(), createHeadForJsonContentType(requestBody), String.class);
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
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType, Object... uriVariables) {
        ResponseEntity<String> responseEntity  = null;
        responseEntity = new RestTemplate().postForEntity(url, createHeadForJsonContentType(requestBody), String.class, uriVariables);
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
     * @return  封装好的 ApiData 实体类
     * @param <T>  限定返回类型
     * @param <S>  请求数据实体类泛型
     */
    public static <T extends OneBotApiData, S> T postOneBotForEntity(String url, S requestBody, Class<T> responseDataType, Map<String, ?> uriVariables) {
        ResponseEntity<String> responseEntity  = null;
        responseEntity = new RestTemplate().postForEntity(url, createHeadForJsonContentType(requestBody), String.class, uriVariables);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return parseJsonResult(responseEntity, responseDataType);
        } else {
            throw new RuntimeException("Failed to post data to " + url + ". Status code: " + responseEntity.getStatusCode());
        }
    }

    /**
     * 处理 OneBot 响应
     * @param response  OneBot 响应体
     * @return  ApiData 中的 data 字段
     * @param <T>  限制入参实现了 OneBotApiData 接口
     */
    private static <T extends OneBotApiData> T checkOneBotResponse(ApiData<T> response) {
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
     * 将 JSON 字符串格式的响应结果解析为 ApiData<T> 类型
     * @param responseEntity  响应体
     * @param responseDataType  需要获取的 OneBot API 响应结果的 data 字段类型
     * @return  ApiData<T> 类型的响应结构
     * @param <T>  限定返回值为 OneBotApiData 类型
     */
    public static <T extends OneBotApiData> T parseJsonResult(ResponseEntity<String> responseEntity, Class<T> responseDataType) {
        String result = responseEntity.getBody();
        ApiData<T> apiData = JSONUtil.toBean(result, ApiData.class);
        return checkOneBotResponse(apiData.build(JSONUtil.toBean((JSONObject) apiData.getData(), responseDataType)));
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
