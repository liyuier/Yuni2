package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.sender.GroupMessageSender;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: YuniLogUtils
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/21 0:56
 * @description: 简单日志功能封装
 */

@Slf4j
public class YuniLogUtils {

    /**
     * 拼装收到消息的日志字符串
     * @param event  消息事件
     * @return  日志字符串
     * {sender} 于 {position} 发送消息 {}
     */
    public static String receiveMessageLogStr(MessageEvent<?> event) {
        String timeStr = "";
        String senderStr = "";
        // 介词，用于输出群聊消息时在群号前加个 "于" 字
        String preposition = "";
        String groupStr = "";
        String sendMessageAction = "";
        String messageStr = "";
        // 拼装消息发送时间
        timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                                .format(new Date(event.getTime() * 1000L));
        MessageSender sender = event.getSender();

        if (sender instanceof GroupMessageSender) {
            // 拼装消息发送人
            String groupCard = ((GroupMessageSender) sender).getCard();
            senderStr = StringUtils.hasText(groupCard) ?
                    groupCard : sender.getNickname();
            // 拼装发送位置
            preposition = "于 ";
            groupStr = ((GroupMessageEvent) event).getGroupId() + " ";
            sendMessageAction = "发送消息";
        } else {
            // 拼装消息发送人
            senderStr = sender.getNickname();
            // 拼装发送位置
            sendMessageAction = "向机器人发送私聊消息";
        }
        senderStr += "<" + sender.getUserId() + ">";

        // 拼装消息
        messageStr = event.getMessageChain().toString();

        // 拼装最终日志
        String logStr = timeStr + " " +  // 时间
                buildBrightRedLog(senderStr) + " " +  // 消息发送人
                preposition +  // 如果是群消息，这里加一个 “于”
                buildCyanLog(groupStr) +  // 如果是群消息，这里是群号
                sendMessageAction + " " +  // 发消息的具体描述，私聊与群聊不同
                buildBrightBlueLog(messageStr);

        // 特殊符号的处理
        return escapeString(logStr);
    }

    // 控制台输出字符串时设置颜色 👇

    // 靛青
    private static String buildCyanLog(String input) {
        return "\033[36m" + input + "\033[0m";
    }

    // 亮红
    private static String buildBrightRedLog(String input) {
        return "\033[91m" + input + "\033[0m";
    }

    // 亮蓝
    private static String buildBrightBlueLog(String input) {
        return "\033[92m" + input + "\033[0m";
    }

    /**
     * 特殊字符转换
     * @param input 含有特殊字符的字符串
     * @return 处理后的字符串
     */
    private static String escapeString(String input) {
        StringBuilder escaped = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                default:
                    escaped.append(c);
            }
        }
        return escaped.toString();
    }
}
