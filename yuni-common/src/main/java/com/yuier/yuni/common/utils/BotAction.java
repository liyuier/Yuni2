package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.bot.YuniBot;
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
        YuniBot bot = ThreadLocalUtil.getBot();
        return bot.getOnebotUrl();
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
                getOneBotBaseUrl() + "/get_msg",
                GetMessageResData.class,
                getMessageParam
        );
    }

    /**
     * 获取回复的目标消息的发送者的 ID
     * 因为回复消息一般都自带一个 @ 消息，需要将这个 @ 消息删除
     * @param replyTargetMessageId  回复的目标消息的 ID
     * @return  目标消息的发送者的 ID
     */
    public static Long getReplayTargetSenderId(Long replyTargetMessageId) {
        // TODO 这里应该先去数据库里查消息

        // 数据库里查不到，请求 OneBot API
        GetMessageResData messageData = getMessage(replyTargetMessageId);
        return messageData.getSender().getUserId();
    }

    public static GetLoginInfoResData getLoginInfo() {
        return CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "/get_login_info",
                GetLoginInfoResData.class
        );
    }

    public static GetLoginInfoResData getLoginInfo(String url) {
        return CallOneBotUtil.getOneBotForEntity(
                url + "/get_login_info",
                GetLoginInfoResData.class
        );
    }

}
