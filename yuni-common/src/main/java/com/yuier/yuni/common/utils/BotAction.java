package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.onebotapi.data.GetLoginInfoResData;
import com.yuier.yuni.common.domain.onebotapi.data.GetMessageResData;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
        return CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "get_msg",
                GetMessageResData.class,
                getMessageParam
        );
    }

    public static GetLoginInfoResData getLoginInfo() {
        return CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "get_login_info",
                GetLoginInfoResData.class
        );
    }

}
