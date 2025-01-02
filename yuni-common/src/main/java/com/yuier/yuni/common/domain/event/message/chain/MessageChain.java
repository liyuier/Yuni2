package com.yuier.yuni.common.domain.event.message.chain;

import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.AtData;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * @Title: MessageChain
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/10 23:24
 * @description: 消息链
 */

@Data
@AllArgsConstructor
public class MessageChain {

    private ArrayList<MessageSeg<?>> content;

    private HashMap<String, ArrayList<Integer>> segTypeIndexes;

    public MessageChain() {
        content = new ArrayList<>();
        segTypeIndexes = new HashMap<>();
    }

    public MessageChain(ArrayList<MessageSeg<?>> messageSegList) {
        this();
        content = messageSegList;
    }

    public MessageChain(MessageSeg<?>[] messageSegs) {
        this();
        content.addAll(Arrays.asList(messageSegs));
    }

    /**
     * 用于接收到消息，组装消息链时使用
     * 可以维护一个每种消息在 content 中的索引列表，很方便
     * @param messageSegList  原始消息事件中的消息段
     * @param buildSegTypeIndexes  是否维护 segTypeIndexes
     */
    public MessageChain(ArrayList<MessageSeg<?>> messageSegList, Boolean buildSegTypeIndexes) {
        this();
        for (int i = 0; i < messageSegList.size(); i++) {
            MessageSeg<?> seg = messageSegList.get(i);
            String type = seg.getType();
            content.add(seg);
            ArrayList<Integer> indexList = segTypeIndexes.getOrDefault(type, new ArrayList<>());
            indexList.add(i);
            segTypeIndexes.put(type, indexList);
        }
    }

    public MessageChain(String text) {
        this();
        content.add(new TextSeg(text));
    }

    public void addTextSeg(String text) {
        content.add(new TextSeg(
                new TextData(text)
        ));
    }

    public Boolean startWithTextData() {
        return content.get(FIRST_INDEX).typeOf(MessageDataEnum.TEXT) &&
                !((TextSeg) content.get(FIRST_INDEX)).getData().getText().trim().isEmpty();
    }

    public Boolean startWithReplyData() {
        return content.get(FIRST_INDEX).typeOf(MessageDataEnum.REPLY);
    }

    /**
     * 获取开头的文本消息段
     * @return  开头的文本消息段
     */
    public TextData getStartTextData() {
        return ((TextSeg) content.get(FIRST_INDEX)).getData();
    }

    public Boolean containsMessageType(MessageDataEnum dataEnum) {
        return segTypeIndexes.containsKey(dataEnum.getTypeName());
    }

    /**
     * @return  是否包含 at 消息
     */
    public Boolean atSomeOne() {
        return containsMessageType(MessageDataEnum.AT);
    }

    /**
     * @return  是否包含 at bot 消息
     */
    public Boolean atBot() {
        if (!atSomeOne()) {
            return false;
        }
        ArrayList<Integer> atSegIndexes = segTypeIndexes.get(MessageDataEnum.AT.getTypeName());
        for (Integer index : atSegIndexes) {
            MessageSeg<AtData> seg = (MessageSeg<AtData>) content.get(index);
            if (Long.parseLong(seg.getData().getQq()) == ThreadLocalUtil.getBot().getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 消息链中是否包含指定字符串
     * @param str 指定字符串
     * @return 是否包含
     */
    public Boolean contains(String str) {
        for (MessageSeg<?> seg : this.content) {
            if (seg.typeOf(MessageDataEnum.TEXT)) {
                TextData data = (TextData) seg.getData();
                if (data.getText().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换消息链中字符串元素
     * @param formerStr 原字符串
     * @param latterStr 新字符串
     */
    public void replace(String formerStr, String latterStr) {
        for (MessageSeg<?> seg : this.content) {
            if (seg.typeOf(MessageDataEnum.TEXT)) {
                TextData data = (TextData) seg.getData();
                data.setText(data.getText().replace(formerStr, latterStr));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg<?> seg : content) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }
}
