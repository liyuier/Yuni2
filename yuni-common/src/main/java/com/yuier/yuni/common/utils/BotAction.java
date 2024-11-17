package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.bot.YuniBot;
import com.yuier.yuni.common.domain.event.message.MessageEventPosition;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.onebotapi.data.*;
import com.yuier.yuni.common.domain.onebotapi.pojo.SendGroupMessagePojo;
import com.yuier.yuni.common.domain.onebotapi.pojo.SendPrivateMessagePojo;
import com.yuier.yuni.common.enums.MessageTypeEnum;
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

    /**
     * 在项目启动后，处理一条上报消息时获取登录信息
     * 因为这时候 ThreadLocal 中已经保存了 OneBot 客户端的 url 了
     * @return  OneBot 客户端登录信息
     */
    public static GetLoginInfoResData getLoginInfo() {
        return CallOneBotUtil.getOneBotForEntity(
                getOneBotBaseUrl() + "/get_login_info",
                GetLoginInfoResData.class
        );
    }

    /**
     * 在项目启动时，根据配置文件中配置的 OneBot 客户端登录账号获取登录信息
     * @param url  根据配置文件中配置的 OneBot 根 url
     * @return  OneBot 客户端登录信息
     */
    public static GetLoginInfoResData getLoginInfo(String url) {
        return CallOneBotUtil.getOneBotForEntity(
                url + "/get_login_info",
                GetLoginInfoResData.class
        );
    }

    /**
     * 发送群聊消息
     * @param groupId  群号
     * @param messageSegs 消息内容
     * @return 消息 ID
     */
    public static SendGroupMessageResData sendGroupMessage(Long groupId, MessageSeg<?>[] messageSegs) {
        String sendGroupMessageUrl = getOneBotBaseUrl() + "/send_group_msg";
        return CallOneBotUtil.postOneBotForEntity(
                sendGroupMessageUrl,
                new SendGroupMessagePojo(groupId, messageSegs),
                SendGroupMessageResData.class
        );
    }

    /**
     * 发送群聊消息
     * @param groupId  群号
     * @param chain 消息链
     * @return 消息 ID
     */
    public static SendGroupMessageResData sendGroupMessage(Long groupId, MessageChain chain) {
        String sendGroupMessageUrl = getOneBotBaseUrl() + "/send_group_msg";
        return CallOneBotUtil.postOneBotForEntity(
                sendGroupMessageUrl,
                new SendGroupMessagePojo(groupId, chain),
                SendGroupMessageResData.class
        );
    }

    /**
     * 发送私聊消息
     * @param userId  对方用户 ID
     * @param messageSegs 消息内容
     * @return 消息 ID
     */
    public static SendPrivateMessageResData sendPrivateMessageResData(Long userId, MessageSeg<?>[] messageSegs) {
        String sendPrivateMessageUrl = getOneBotBaseUrl() + "send_private_msg";
        return CallOneBotUtil.postOneBotForEntity(
                sendPrivateMessageUrl,
                new SendPrivateMessagePojo(userId, messageSegs),
                SendPrivateMessageResData.class
        );
    }

    /**
     * 发送私聊消息
     * @param userId  对方用户 ID
     * @param chain 消息链
     * @return 消息 ID
     */
    public static SendPrivateMessageResData sendPrivateMessageResData(Long userId, MessageChain chain) {
        String sendPrivateMessageUrl = getOneBotBaseUrl() + "send_private_msg";
        return CallOneBotUtil.postOneBotForEntity(
                sendPrivateMessageUrl,
                new SendPrivateMessagePojo(userId, chain),
                SendPrivateMessageResData.class
        );
    }

    /**
     * 发送消息的统一接口
     * @param position 消息位置
     * @param messageSegs  消息段数组
     * @return 消息 ID
     */
    public static SendMessageResData sendMessage(MessageEventPosition position, MessageSeg<?>[] messageSegs) {
        MessageTypeEnum messageType = position.getMessageType();
        if (messageType.equals(MessageTypeEnum.GROUP)) {
            return new SendMessageResData(sendGroupMessage(position.getPosition(), messageSegs));
        } else {
            return new SendMessageResData((sendPrivateMessageResData(position.getPosition(), messageSegs)));
        }
    }

    /**
     * 发送消息的统一接口
     * @param position 消息位置
     * @param chain  消息链
     * @return 消息 ID
     */
    public static SendMessageResData sendMessage(MessageEventPosition position, MessageChain chain) {
        MessageTypeEnum messageType = position.getMessageType();
        if (messageType.equals(MessageTypeEnum.GROUP)) {
            return new SendMessageResData(sendGroupMessage(position.getPosition(), chain));
        } else {
            return new SendMessageResData((sendPrivateMessageResData(position.getPosition(), chain)));
        }
    }
}
