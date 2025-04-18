package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.bot.YuniBot;
import com.yuier.yuni.common.domain.event.OneBotEventPosition;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.onebotapi.data.*;
import com.yuier.yuni.common.domain.onebotapi.pojo.GetGroupMemberInfoPojo;
import com.yuier.yuni.common.domain.onebotapi.pojo.GetMessagePojo;
import com.yuier.yuni.common.domain.onebotapi.pojo.SendGroupMessagePojo;
import com.yuier.yuni.common.domain.onebotapi.pojo.SendPrivateMessagePojo;
import com.yuier.yuni.common.enums.OneBotEventPositionEnum;
import org.springframework.stereotype.Component;

/**
 * @Title: BotAction
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/9 22:26
 * @description: 插件执行完毕，返回一个 BotAction
 */

@Component
public class BotAction {

    /**
     * 获取当前 bot 实例的 OneBot API 基础 url
     * @return
     */
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
        String getMessageUrl = getOneBotBaseUrl() + "/get_msg";
        return CallOneBotUtil.postOneBotForEntity(
                getMessageUrl,
                new GetMessagePojo(messageId),
                GetMessagePojo.class,
                GetMessageResData.class
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
                SendGroupMessagePojo.class,
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
                SendGroupMessagePojo.class,
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
                SendPrivateMessagePojo.class,
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
                SendPrivateMessagePojo.class,
                SendPrivateMessageResData.class
        );
    }

    /**
     * 发送消息的统一接口
     * @param position 消息位置
     * @param messageSegs  消息段数组
     * @return 消息 ID
     */
    public static SendMessageResData sendMessage(OneBotEventPosition position, MessageSeg<?>[] messageSegs) {
        OneBotEventPositionEnum messageType = position.getEventPosition();
        if (messageType.equals(OneBotEventPositionEnum.GROUP)) {
            return new SendMessageResData(sendGroupMessage(position.getPositionId(), messageSegs));
        } else {
            return new SendMessageResData((sendPrivateMessageResData(position.getPositionId(), messageSegs)));
        }
    }

    /**
     * 发送消息的统一接口
     * @param position 消息位置
     * @param chain  消息链
     * @return 消息 ID
     */
    public static SendMessageResData sendMessage(OneBotEventPosition position, MessageChain chain) {
        OneBotEventPositionEnum messageType = position.getEventPosition();
        if (messageType.equals(OneBotEventPositionEnum.GROUP)) {
            return new SendMessageResData(sendGroupMessage(position.getPositionId(), chain));
        } else {
            return new SendMessageResData((sendPrivateMessageResData(position.getPositionId(), chain)));
        }
    }

    /**
     * 获取群成员信息
     * @param groupId  群 ID
     * @param userId  群成员 ID
     * @return  群成员信息
     */
    public static GetGroupMemberInfoResData getGroupMemberInfo(Long groupId, Long userId) {
        return CallOneBotUtil.postOneBotForEntity(
                getOneBotBaseUrl() + "/get_group_member_info",
                new GetGroupMemberInfoPojo(groupId, userId),
                GetGroupMemberInfoPojo.class,
                GetGroupMemberInfoResData.class
        );
    }

    /**
     * 根据群成员 ID 获取群成员昵称
     * @param groupId  群 ID
     * @param userId  群成员 ID
     * @return  群成员昵称
     */
    public static String GetGroupMemberName(Long groupId, Long userId) {
        GetGroupMemberInfoResData groupMemberInfo = getGroupMemberInfo(groupId, userId);
        String card = groupMemberInfo.getCard();
        if (card != null && !card.isEmpty()) {
            return card;
        }
        return groupMemberInfo.getNickname();
    }
}
