package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.onebotapi.ApiData;
import com.yuier.yuni.common.domain.onebotapi.data.GetLoginInfoResData;
import com.yuier.yuni.common.domain.onebotapi.data.GetMessageResData;
import com.yuier.yuni.common.interfaces.onebotapi.OneBotApiData;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.yuier.yuni.common.constants.OneBotApiRetCode.*;

/**
 * @Title: BotAction
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/9 22:26
 * @description: 插件执行完毕，返回一个 BotAction
 */

@Component
public class BotAction {

    private static String getOneBotBaseUrl() {
        return "http://127.0.0.1:3010/";
    }

    /**
     * 调用 OneBot 客户端的 get_message 接口
     * @param messageId  请求的消息 ID
     * @return  GetMessageResData 实例
     */
    public static GetMessageResData getMessage(Long messageId) {
        HashMap<String, Long> getMessageParam = new HashMap<>();
        getMessageParam.put("message_id", messageId);
        return checkOneBotResponse(CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "get_msg",
                GetMessageResData.class,
                getMessageParam
        ));
    }

    public static GetLoginInfoResData getLoginInfo() {
        return checkOneBotResponse(CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "get_login_info",
                GetLoginInfoResData.class
        ));
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

}
